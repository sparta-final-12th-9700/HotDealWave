package com.sparta.hotdeal.product.application.service.product;

import com.sparta.hotdeal.product.application.dtos.req.product.ReqPatchProductQuantityDto;
import com.sparta.hotdeal.product.application.dtos.req.promotion.ReqPromotionQuantityDto;
import com.sparta.hotdeal.product.application.dtos.res.product.ResPatchReduceProductQuantityDto;
import com.sparta.hotdeal.product.application.dtos.res.product.ResPatchRestoreProductQuantityDto;
import com.sparta.hotdeal.product.application.exception.ApplicationException;
import com.sparta.hotdeal.product.application.exception.ErrorCode;
import com.sparta.hotdeal.product.application.service.ProductPromotionHelperService;
import com.sparta.hotdeal.product.domain.entity.product.Product;
import com.sparta.hotdeal.product.domain.repository.product.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductInventoryService {
    private final ProductRepository productRepository;
    private final ProductPromotionHelperService productPromotionHelperService;

    // 재고 차감
    public List<ResPatchReduceProductQuantityDto> reduceQuantity(
            List<ReqPatchProductQuantityDto> reqPatchProductQuantityDto) {
        return processProductQuantityWithRetry(reqPatchProductQuantityDto, false); // 재고 차감
    }

    // 재고 복구
    public List<ResPatchRestoreProductQuantityDto> restoreQuantity(
            List<ReqPatchProductQuantityDto> reqPatchProductQuantityDto) {
        return processProductQuantityWithRetry(reqPatchProductQuantityDto, true); // 재고 복구
    }

    // 재고 처리 공통 메서드
    private <T> List<T> processProductQuantityWithRetry(List<ReqPatchProductQuantityDto> reqPatchProductQuantityDto,
                                                        boolean isRestore) {
        int retryCount = 5;  // 재시도 횟수
        for (int i = 0; i < retryCount; i++) {
            try {
                return processProductQuantity(reqPatchProductQuantityDto, isRestore);
            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.error("Optimistic Lock exception occurred on attempt {}. Retrying...", i + 1);

                // 마지막 재시도에서 실패하면 예외를 던진다
                if (i == retryCount - 1) {
                    log.error("Max retries reached. OptimisticLockException on final attempt.");
                    throw new ApplicationException(ErrorCode.PRODUCT_CONCURRENT_MODIFICATION_EXCEPTION);
                }

                // 재시도 전에 잠시 대기
                try {
                    Thread.sleep(200 * (long) Math.pow(2, i));  // 대기 시간을 조금 늘려 재시도
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return new ArrayList<>();
    }

    // 상품 수량 처리
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected <T> List<T> processProductQuantity(
            List<ReqPatchProductQuantityDto> reqPatchProductQuantityDto,
            boolean isRestore) {

        // 상품 ID 목록 추출
        List<UUID> productIds = reqPatchProductQuantityDto.stream()
                .map(ReqPatchProductQuantityDto::getProductId)
                .collect(Collectors.toList());

        // 상품들 조회
        List<Product> products = productRepository.findAllByIdIn(productIds);

        // 상품이 없으면 예외 처리
        if (products.size() != reqPatchProductQuantityDto.size()) {
            throw new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION);
        }

        // 할인 중인 상품을 분리
        List<ReqPromotionQuantityDto> reqPromotionQuantityDtos = products.stream()
                .filter(product -> product.getDiscountPrice() != null) // 할인 중인 상품만 필터
                .map(product -> {
                    // 해당 상품의 ID와 수량을 ReqPromotionQuantityDto로 매핑
                    int quantity = reqPatchProductQuantityDto.stream()
                            .filter(dto -> dto.getProductId().equals(product.getId()))
                            .map(ReqPatchProductQuantityDto::getQuantity)
                            .findFirst()
                            .orElse(0); // 수량을 찾고, 없으면 0으로 기본 설정
                    return new ReqPromotionQuantityDto(product.getId(), quantity);
                })
                .collect(Collectors.toList());

        log.info("ProductService reqPromotionQuantityDtos : {}", reqPromotionQuantityDtos.size());

        // 할인 중인 상품에 대한 별도 요청 처리
        processDiscountedProducts(reqPromotionQuantityDtos, isRestore);

        List<T> resPatchProductQuantityDtos = new ArrayList<>();

        // 상품 수량 처리
        for (ReqPatchProductQuantityDto dto : reqPatchProductQuantityDto) {
            Product product = findProductById(dto.getProductId(), products);

            try {
                processSingleProduct(product, dto.getQuantity(), isRestore);
            } catch (OptimisticLockException e) {
                throw new ApplicationException(ErrorCode.PRODUCT_CONCURRENT_MODIFICATION_EXCEPTION);
            }

            // 결과 DTO 생성 후 추가
            if (isRestore) {
                resPatchProductQuantityDtos.add((T) ResPatchRestoreProductQuantityDto.of(product.getId()));
            } else {
                resPatchProductQuantityDtos.add((T) ResPatchReduceProductQuantityDto.of(product.getId()));
            }
        }

        return resPatchProductQuantityDtos;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void processSingleProduct(Product product, int quantity, boolean isRestore) {
        if (isRestore) {
            product.increaseQuantity(quantity);
        } else {
            product.decreaseQuantity(quantity);
        }
        productRepository.save(product);
    }

    private Product findProductById(UUID productId, List<Product> products) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND_EXCEPTION));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void processDiscountedProducts(List<ReqPromotionQuantityDto> reqPromotionQuantityDtos,
                                             Boolean isRestore) {
        productPromotionHelperService.processPromotionQuantity(reqPromotionQuantityDtos, isRestore);
    }
}

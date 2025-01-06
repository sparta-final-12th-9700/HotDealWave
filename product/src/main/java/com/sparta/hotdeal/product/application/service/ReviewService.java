package com.sparta.hotdeal.product.application.service;

import com.sparta.hotdeal.product.application.dtos.req.review.ReqPostReviewDto;
import com.sparta.hotdeal.product.application.exception.ApplicationException;
import com.sparta.hotdeal.product.application.exception.ErrorCode;
import com.sparta.hotdeal.product.application.service.client.OrderClientService;
import com.sparta.hotdeal.product.domain.entity.product.File;
import com.sparta.hotdeal.product.domain.entity.review.Review;
import com.sparta.hotdeal.product.domain.repository.product.ProductRepository;
import com.sparta.hotdeal.product.domain.repository.review.ReviewRepository;
import com.sparta.hotdeal.product.infrastructure.dtos.ResGetOrderByIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final OrderClientService orderClientService;
    private final FileService fileService;
    private final SubFileService subFileService;

    public void createReview(ReqPostReviewDto reqPostReviewDto) {
        // (1) 유효한 주문 확인
        ResGetOrderByIdDto fetchedOrder = orderClientService.getOrder(reqPostReviewDto.getOrderId());

        // (2) 유효한 상품 확인
        productRepository.findById(reqPostReviewDto.getProductId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.NOT_FOUND_EXCEPTION));

        File reviewImgs = fileService.saveFile();
        for (MultipartFile file : reqPostReviewDto.getReviewImgs()) {
            subFileService.saveImg(file, reviewImgs);
        }

        Review review = Review.create(reqPostReviewDto.getOrderId(), reqPostReviewDto.getProductId(),
                reqPostReviewDto.getRating(), reqPostReviewDto.getReview(), reviewImgs);

        reviewRepository.save(review);
    }
}

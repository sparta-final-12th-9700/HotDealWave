package com.sparta.hotdeal.coupon.application.service;

import com.sparta.hotdeal.coupon.application.dto.req.ReqPostCouponValidateDto;
import com.sparta.hotdeal.coupon.application.dto.req.ReqPostCouponsIssueDto;
import com.sparta.hotdeal.coupon.application.dto.res.ResGetUserCouponsDto;
import com.sparta.hotdeal.coupon.application.dto.res.ResPostCouponValidateDto;
import com.sparta.hotdeal.coupon.application.exception.CustomException;
import com.sparta.hotdeal.coupon.application.exception.ErrorCode;
import com.sparta.hotdeal.coupon.application.mapper.CouponMapper;
import com.sparta.hotdeal.coupon.domain.entity.Coupon;
import com.sparta.hotdeal.coupon.domain.entity.CouponInfo;
import com.sparta.hotdeal.coupon.domain.entity.CouponStatus;
import com.sparta.hotdeal.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponInfoService couponInfoService;
    private final CouponRepository couponRepository;
    private final RedissonClient redissonClient;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void issueFirstComeFirstServeCoupon(UUID userId, ReqPostCouponsIssueDto reqDto) {
        String lockKey = "LOCK_COUPON_" + reqDto.getCouponInfoId();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean available = lock.tryLock(10, 5, TimeUnit.SECONDS);
            if (!available) {
                throw new CustomException(ErrorCode.REQUEST_TIMEOUT);
            }

            // 1. 쿠폰 정보 조회
            CouponInfo couponInfo = couponInfoService.findByIdOrThrow(reqDto.getCouponInfoId());
            // 2. 유효성 검증: 쿠폰 상태 확인
            if (couponInfo.getStatus() != CouponStatus.ISSUED) {
                throw new CustomException(ErrorCode.INVALID_COUPON_STATUS);
            }
            // 3. 발급 가능 수량 확인
            if (couponInfo.getIssuedCount() >= couponInfo.getQuantity()) {
                throw new CustomException(ErrorCode.COUPON_OUT_OF_STOCK);
            }
            // 4. 사용자 중복 발급 확인
            boolean alreadyIssued = couponRepository.existsByUserIdAndCouponInfoId(userId, couponInfo.getId());
            if (alreadyIssued) {
                throw new CustomException(ErrorCode.ALREADY_ISSUED_COUPON);
            }
            // 5. 쿠폰 발급 처리
            couponInfo.incrementIssuedCount();
            // 6. 쿠폰 엔티티 저장
            Coupon coupon = Coupon.builder()
                    .userId(userId)
                    .couponInfo(couponInfo)
                    .dailyIssuedDate(LocalDate.now())
                    .isUsed(false)
                    .usedDate(null)
                    .build();
            couponRepository.save(coupon);
        } catch (InterruptedException e) {
            throw new RuntimeException("Redis 락 처리 중 오류 발생", e);
        } finally {
            // 락 해제
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("unlock complete: {}", lock.getName());
            }
        }
    }

    public ResPostCouponValidateDto validateCoupon(UUID couponId, ReqPostCouponValidateDto reqDto) {
        Coupon coupon = findByIdOrThrow(couponId);
        CouponInfo couponInfo = couponInfoService.findByIdOrThrow(coupon.getCouponInfo().getId());

        if (coupon.isUsed()) {
            throw new CustomException(ErrorCode.COUPON_ALREADY_USED);
        }

        if (couponInfo.getExpirationDate() != null && couponInfo.getExpirationDate().isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.COUPON_EXPIRED);
        }

        int applicableTotalPrice;
        if (couponInfo.getCompanyId() == null) {
            applicableTotalPrice = reqDto.getProducts().stream()
                    .mapToInt(product -> product.getPrice() * product.getQuantity())
                    .sum();
        } else {
            applicableTotalPrice = reqDto.getProducts().stream()
                    .filter(product -> product.getCompanyId().equals(couponInfo.getCompanyId()))
                    .mapToInt(product -> product.getPrice() * product.getQuantity())
                    .sum();

            if (applicableTotalPrice == 0) {
                throw new CustomException(ErrorCode.INVALID_COUPON_COMPANY);
            }
        }

        if (applicableTotalPrice < couponInfo.getMinOrderAmount()) {
            throw new CustomException(ErrorCode.MIN_ORDER_AMOUNT_NOT_MET);
        }

        return ResPostCouponValidateDto.builder()
                .isValid(true)
                .totalDiscountAmount(couponInfo.getDiscountAmount())
                .build();
    }

    @Transactional
    public void useCoupon(UUID couponId) {
        Coupon coupon = findByIdOrThrow(couponId);

        if (coupon.isUsed()) {
            throw new CustomException(ErrorCode.COUPON_ALREADY_USED);
        }

        coupon.useCoupon();
    }

    @Transactional
    public void recoverCoupon(UUID couponId) {
        Coupon coupon = findByIdOrThrow(couponId);

        if (!coupon.isUsed()) {
            throw new CustomException(ErrorCode.COUPON_NOT_USED);
        }

        coupon.recoverCoupon();
    }

    @Transactional
    public void deleteCoupon(UUID couponId, String email) {
        Coupon coupon = couponRepository.findByIdAndDeletedAtIsNull(couponId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COUPON));

        coupon.delete(email);
    }

    public List<ResGetUserCouponsDto> getUserCoupons(UUID userId, boolean isUsed) {
        List<Coupon> coupons = couponRepository.findByUserIdAndDeletedAtIsNullAndIsUsed(userId, isUsed);

        return CouponMapper.toResGetUserCouponsDtoList(coupons);
    }

    public ResGetUserCouponsDto getUserCouponDetail(UUID couponId) {
        Coupon coupon = findByIdOrThrow(couponId);

        return CouponMapper.toResGetUserCouponsDto(coupon);
    }


    public Coupon findByIdOrThrow(UUID couponId) {
        return couponRepository.findByIdAndDeletedAtIsNull(couponId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COUPON));
    }

}


package com.sparta.hotdeal.coupon.application.mapper;

import com.sparta.hotdeal.coupon.application.dto.req.ReqPostCouponInfosDto;
import com.sparta.hotdeal.coupon.application.dto.res.ResGetCouponInfosByIdDto;
import com.sparta.hotdeal.coupon.application.dto.res.ResGetCouponInfosDto;
import com.sparta.hotdeal.coupon.domain.entity.CouponInfo;
import com.sparta.hotdeal.coupon.domain.entity.CouponStatus;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CouponInfoMapper {

    // 쿠폰 생성 시
    public static CouponInfo toEntity(ReqPostCouponInfosDto dto) {
        return CouponInfo.builder()
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .discountAmount(dto.getDiscountAmount())
                .minOrderAmount(dto.getMinOrderAmount())
                .expirationDate(dto.getExpirationDate())
                .status(CouponStatus.PENDING)
                .couponType(dto.getCouponType())
                .companyId(dto.getCompanyId())
                .build();
    }

    // 발급 쿠폰 단건 조회
    public static ResGetCouponInfosByIdDto toResGetCouponInfosByIdDto(CouponInfo couponInfo) {
        return ResGetCouponInfosByIdDto.builder()
                .couponId(couponInfo.getId())
                .name(couponInfo.getName())
                .quantity(couponInfo.getQuantity())
                .issuedCount(couponInfo.getIssuedCount())
                .discountAmount(couponInfo.getDiscountAmount())
                .minOrderAmount(couponInfo.getMinOrderAmount())
                .expirationDate(couponInfo.getExpirationDate())
                .status(couponInfo.getStatus().name())
                .couponType(couponInfo.getCouponType().name())
                .companyId(couponInfo.getCompanyId())
                .build();
    }

    // 쿠폰 정보 목록 조회
    public static Page<ResGetCouponInfosDto> toResGetCouponInfosPage(Page<CouponInfo> couponInfos) {
        return couponInfos.map(couponInfo -> ResGetCouponInfosDto.builder()
                .couponId(couponInfo.getId())
                .name(couponInfo.getName())
                .quantity(couponInfo.getQuantity())
                .issuedCount(couponInfo.getIssuedCount())
                .discountAmount(couponInfo.getDiscountAmount())
                .minOrderAmount(couponInfo.getMinOrderAmount())
                .expirationDate(couponInfo.getExpirationDate())
                .status(couponInfo.getStatus().name())
                .couponType(couponInfo.getCouponType().name())
                .companyId(couponInfo.getCompanyId())
                .build()
        );
    }
}

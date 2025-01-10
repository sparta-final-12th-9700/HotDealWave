package com.sparta.hotdeal.coupon.presentation.controller;

import com.sparta.hotdeal.coupon.application.dto.req.*;
import com.sparta.hotdeal.coupon.application.dto.res.*;
import com.sparta.hotdeal.coupon.application.dto.ResponseDto;
import com.sparta.hotdeal.coupon.application.service.CouponInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/coupon-infos")
@RequiredArgsConstructor
public class CouponInfoController {

    private final CouponInfoService couponInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_MASTER", "ROLE_MANAGER"})
    public ResponseDto<ResPostCouponInfosDto> createCoupon(@RequestBody ReqPostCouponInfosDto reqDto) {
        ResPostCouponInfosDto responseDto = couponInfoService.createCoupon(reqDto);
        return ResponseDto.of("쿠폰 정보 생성 성공", responseDto);
    }

    @PutMapping("/{couponInfoId}")
    @Secured({"ROLE_MASTER", "ROLE_MANAGER"})
    public ResponseDto<Void> updateCoupon(@PathVariable UUID couponInfoId, @RequestBody ReqPutCouponInfosByIdDto reqDto) {
        couponInfoService.updateCoupon(couponInfoId, reqDto);
        return ResponseDto.of("쿠폰 정보 수정 성공", null);
    }

    @PatchMapping("/{couponInfoId}/status")
    @Secured({"ROLE_MASTER", "ROLE_MANAGER"})
    public ResponseDto<Void> updateCouponStatus(@PathVariable UUID couponInfoId, @RequestBody ReqPatchCouponInfosByIdStatusDto reqDto) {
        couponInfoService.updateCouponStatus(couponInfoId, reqDto.getStatus());
        return ResponseDto.of("쿠폰 정보의 상태 변경 성공", null);
    }

    @GetMapping("/{couponInfoId}")
    public ResponseDto<ResGetCouponInfosByIdDto> getCouponDetail(@PathVariable UUID couponInfoId) {
        ResGetCouponInfosByIdDto responseDto = couponInfoService.getCouponDetail(couponInfoId);
        return ResponseDto.of("쿠폰 정보 상세 조회 성공", responseDto);
    }

    @GetMapping
    public ResponseDto<ResGetCouponInfosDto> getCoupons() {
        ResGetCouponInfosDto responseDto = ResGetCouponInfosDto.builder()
                .coupons(List.of(
                        ResGetCouponInfosDto.CouponDetailDto.builder()
                                .couponId(UUID.randomUUID())
                                .name("쿠폰 1")
                                .quantity(100)
                                .issuedCount(50)
                                .discountAmount(5000)
                                .minOrderAmount(50000)
                                .expirationDate(null)
                                .status("ISSUED")
                                .couponType("FIRST_COME_FIRST_SERVE")
                                .companyId(UUID.randomUUID())
                                .build()
                ))
                .page(PageInfoDto.builder()
                        .size(10)
                        .number(1)
                        .totalElements(100)
                        .totalPages(10)
                        .build())
                .build();

        return ResponseDto.of("쿠폰 정보 목록 조회 성공", responseDto);
    }

}

package com.sparta.hotdeal.order.infrastructure.dtos.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResPostCouponValidateDto {
    private boolean isValid;
    private int totalDiscountAmount;
}

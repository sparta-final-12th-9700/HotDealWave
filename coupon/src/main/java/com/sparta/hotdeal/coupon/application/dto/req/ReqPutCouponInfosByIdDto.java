package com.sparta.hotdeal.coupon.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqPutCouponInfosByIdDto {
    private String name;
    private int quantity;
    private int discountAmount;
    private int minOrderAmount;
    private LocalDate expirationDate;
    private String couponType; // Enum as String
    private UUID companyId;
}

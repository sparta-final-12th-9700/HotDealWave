package com.sparta.hotdeal.product.application.dtos.req.review;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ReqPostReviewDto {
    private UUID orderId;
    private UUID productId;
    private Long userId;
    private double rating;
    private String review;
    private List<MultipartFile> reviewImgs;
}

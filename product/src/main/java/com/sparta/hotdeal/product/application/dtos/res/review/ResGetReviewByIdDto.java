package com.sparta.hotdeal.product.application.dtos.res.review;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ResGetReviewByIdDto {
    private String nickname;
    private double rating;
    private String review;
    private List<String> review_imgs;
}

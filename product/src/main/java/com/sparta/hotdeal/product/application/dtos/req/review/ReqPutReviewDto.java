package com.sparta.hotdeal.product.application.dtos.req.review;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ReqPutReviewDto {
    private double rating;
    private String review;
    private List<MultipartFile> reviewImgs;
}

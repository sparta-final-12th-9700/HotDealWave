package com.sparta.hotdeal.product.domain.repository.review;

import com.sparta.hotdeal.product.domain.entity.review.Review;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository {
    Review save(Review review);
}

package com.sparta.hotdeal.product.infrastructure.repository.product;

import com.sparta.hotdeal.product.domain.entity.product.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryJpa extends JpaRepository<Product, UUID> {
}

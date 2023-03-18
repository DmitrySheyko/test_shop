package com.example.test_shop.rate.repository;

import com.example.test_shop.rate.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);
}

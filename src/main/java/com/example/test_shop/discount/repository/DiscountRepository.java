package com.example.test_shop.discount.repository;

import com.example.test_shop.discount.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
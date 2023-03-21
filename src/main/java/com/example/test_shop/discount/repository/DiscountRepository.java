package com.example.test_shop.discount.repository;

import com.example.test_shop.discount.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of repository for {@link Discount} entity
 *
 * @author DmitrySheyko
 */
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
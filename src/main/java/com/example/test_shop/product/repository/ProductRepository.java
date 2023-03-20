package com.example.test_shop.product.repository;

import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Interface of repository for {@link Product} entity
 *
 * @author DmitrySheyko
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findAllByDiscount(Discount discount);

}

package com.example.test_shop.purchase.repository;

import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Interface of repository for {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Set<Purchase> findAllByBuyer(User buyer);

    Optional<Purchase> findFirstByBuyerIdAndProductId(Long BuyerId, long product);

    Set<Purchase> findAllBySeller(User buyer);

}

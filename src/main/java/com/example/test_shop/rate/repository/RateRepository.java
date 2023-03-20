package com.example.test_shop.rate.repository;

import com.example.test_shop.rate.model.Rate;
import com.example.test_shop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface of repository for {@link Rate}
 *
 * @author DmitrySheyko
 */
public interface RateRepository extends JpaRepository<Rate, Long> {

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    Optional<Rate> findByIdAndUser(Long rateId, User user);

}

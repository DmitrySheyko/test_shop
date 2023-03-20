package com.example.test_shop.user.repository;

import com.example.test_shop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Class of repository for {@link User}
 *
 * @author DmitrySheyko
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String email);

}
package com.example.test_shop.user.repository;

import com.example.test_shop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
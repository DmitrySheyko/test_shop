package com.example.test_shop.security;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.repository.UserRepository;
import com.example.test_shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddAdmin implements CommandLineRunner {

    private final UserService service;
    private final UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (!repository.existsByUsername("admin")) {
            NewUserDto admin = NewUserDto.builder()
                    .username("admin")
                    .password("admin")
                    .email("admin@email.ru")
                    .role("ROLE_ADMIN")
                    .build();
            service.add(admin);
        }
        System.out.println("Admin username: admin \nAdmin password: admin");
    }

}
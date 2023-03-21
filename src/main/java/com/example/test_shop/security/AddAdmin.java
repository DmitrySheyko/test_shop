package com.example.test_shop.security;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddAdmin implements CommandLineRunner {
    private final UserService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        NewUserDto user = NewUserDto.builder()
                .username("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .role("ADMIN")
                .email("admin@email.com")
                .build();

        service.add(user);
        System.out.println("Admin username: admin \nAdmin password: admin");

    }

}
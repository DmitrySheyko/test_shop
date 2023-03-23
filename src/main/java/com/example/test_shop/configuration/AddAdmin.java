package com.example.test_shop.configuration;

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
    private final AppProperties properties;

    @Override
    public void run(String... args) throws Exception {
        if (!repository.existsByUsernameAndRole("admin", "ROLE_ADMIN")) {
            NewUserDto admin = NewUserDto.builder()
                    .username(properties.getAdminUsername())
                    .password(properties.getAdminPassword())
                    .email(properties.getAdminEmail())
                    .role("ROLE_ADMIN")
                    .build();
            service.add(admin);
        }
        // Вывод данных администратора на экран добавлен для удобства проверяющего
        System.out.printf("Admin username: %s \nAdmin password: %s%n", properties.getAdminUsername(),
                properties.getAdminPassword());
    }

}
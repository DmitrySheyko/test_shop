package com.example.test_shop.user.controller;

import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class of user's controller for {@link User}
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}")
public class UserPrivateController {

    private final UserService service;

    // Получение собственной сущности User
    @GetMapping
    public UserDto getOwnAccount(@Positive @PathVariable(value = "userId") Long userId) {
        return service.getOwnAccount(userId);
    }

    // Получение данных о другом пользователе
    @GetMapping("/{anotherUserId}")
    public UserDto getAnotherUser(@Positive @PathVariable(value = "userId") Long userId,
                                 @Positive @PathVariable(value = "anotherUserId") Long anotherUserId) {
        return service.getAnotherUser(userId, anotherUserId);
    }

}

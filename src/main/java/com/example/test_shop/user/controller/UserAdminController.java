package com.example.test_shop.user.controller;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Class of admin controller for {@link User}
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserAdminController {
    private final UserServiceImpl service;

    // Добавление нового пользователя
    @PostMapping
    public UserDto add(@Valid @RequestBody NewUserDto userDto) {
        return service.add(userDto);
    }

    // Обновление пользователя
    @PatchMapping("/{userId}")
    public UserDto update(@Valid @RequestBody UserAdminUpdateDto userDto,
                          @Positive @PathVariable(value = "userId") Long userId) {
        return service.update(userDto, userId);
    }

    // Удаление пользователя
    @DeleteMapping("/{userId}")
    public String delete(@Positive @PathVariable(value = "userId") Long userId) {
        return service.delete(userId);
    }

    // Поиск пользователей по параметрам
    @GetMapping
    public List<UserDto> searchUsers(@RequestParam(value = "usersId", required = false) Set<String> usersId,
                                     @RequestParam(value = "usernames", required = false) Set<String> usernames,
                                     @RequestParam(value = "emails", required = false) Set<String> emails) {
        return service.searchUsers(usersId, usernames, emails);
    }

}
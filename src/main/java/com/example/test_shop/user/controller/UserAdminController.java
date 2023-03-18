package com.example.test_shop.user.controller;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserAdminController {
    private final UserServiceImpl service;

    @PostMapping
    public UserAdminDto add(@Valid NewUserDto userDto) {
        return service.add(userDto);
    }

    @PatchMapping("/{id}")
    public UserAdminDto update(@Valid UserAdminUpdateDto userDto,
                               @Positive @PathVariable(value = "id") Long userId) {
        return service.update(userDto, userId);
    }

    @DeleteMapping("/{id}")
    public String delete(@Positive @PathVariable(value = "id") Long userId) {
        return service.delete(userId);
    }

}

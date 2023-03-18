package com.example.test_shop.user.service;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;

public interface UserService {

    UserAdminDto add(NewUserDto userDto);

    UserAdminDto update(UserAdminUpdateDto userDto, Long userId);

    String delete(Long userId);

}

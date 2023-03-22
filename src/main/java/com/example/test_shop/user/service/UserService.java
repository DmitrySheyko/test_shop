package com.example.test_shop.user.service;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.model.User;

/**
 * Interface of service class for {@link User} entity
 *
 * @author DmitrySheyko
 */
public interface UserService {

    UserDto add(NewUserDto userDto);

    UserDto update(UserAdminUpdateDto userDto, Long userId);

    String delete(Long userId);

    UserDto getOwnAccount(Long userId);

    UserDto getAnotherUser(Long userId, Long anotherUserId);

}

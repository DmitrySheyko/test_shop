package com.example.test_shop.user.service;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserShortDto;
import com.example.test_shop.user.model.User;

import java.util.List;
import java.util.Set;

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

    UserShortDto getAnotherUser(Long userId, Long anotherUserId);

    List<UserDto> searchUsers(Set<Long> usersId, Set<String> usernames, Set<String> emails, Set<String> roles,
                              Double balanceEqual, Double balanceMoreOrEqual, Double balanceLessOrEqual);

}

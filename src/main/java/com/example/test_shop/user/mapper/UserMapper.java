package com.example.test_shop.user.mapper;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserShortDto;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import org.springframework.stereotype.Component;

/**
 * Class of mapper for {@link User}
 *
 * @author DmitrySheyko
 */
@Component
public class UserMapper {

    public static UserAdminDto toUserDto(User user) {
        if (user == null) {
            return null;
        } else {
            return UserAdminDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .balance(user.getBalance())
                    .status(user.getStatus().name())
                    .build();
        }
    }

    public static UserShortDto toUserShortDto(User user) {
        if (user == null) {
            return null;
        } else {
            return UserShortDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
        }
    }


    public static User toUser(NewUserDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            return User.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .role(userDto.getRole())
                    .build();
        }
    }

    public static User toUser(UserAdminUpdateDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            return User.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .status(userDto.getStatus() == null ? null : UserStatus.valueOf(userDto.getStatus()))
                    .balance(userDto.getBalance())
                    .build();
        }
    }
}

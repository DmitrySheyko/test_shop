package com.example.test_shop.user.mapper;

import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.notification.mapper.NotificationMapper;
import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserShortDto;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class of mapper for {@link User}
 *
 * @author DmitrySheyko
 */
@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        } else {
            return UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .balance(user.getBalance())
                    .status(user.getStatus().name())
                    .role(user.getRole())
                    .companiesList(user.getCompaniesList() == null
                            ? Collections.emptyList()
                            : user.getCompaniesList().stream().map(CompanyMapper::toShortDto).collect(Collectors.toList()))
                    .notificationsList(user.getNotificationsList() == null
                            ? Collections.emptyList()
                            : user.getNotificationsList().stream().map(NotificationMapper::toDto).collect(Collectors.toList()))
                    .createdOn(user.getCreatedOn())
                    .updatedOn(user.getUpdatedOn())
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

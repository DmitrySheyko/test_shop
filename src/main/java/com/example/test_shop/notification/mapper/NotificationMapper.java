package com.example.test_shop.notification.mapper;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.user.mapper.UserMapper;
import com.example.test_shop.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class of mapper for {@link Notification} entity
 *
 * @author DmitrySheyko
 */
@Component
@AllArgsConstructor
public class NotificationMapper {


    public static Notification toNotification(NewNotificationDto notificationDto, User user) {
        if (notificationDto == null) {
            return null;
        } else {
            return Notification.builder()
                    .text(notificationDto.getText())
                    .user(user)
                    .build();
        }
    }

    public static NotificationDto toDto(Notification notification) {
        if (notification == null) {
            return null;
        } else {
            return NotificationDto.builder()
                    .id(notification.getId())
                    .text(notification.getText())
                    .user(UserMapper.toUserShortDto(notification.getUser()))
                    .createdOn(notification.getCreatedOn())
                    .build();
        }
    }

}

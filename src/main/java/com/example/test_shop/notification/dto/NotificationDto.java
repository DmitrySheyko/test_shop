package com.example.test_shop.notification.dto;

import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for getting information about {@link Notification} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NotificationDto {

    private Long id;
    private String text;
    private UserShortDto user;
    private LocalDateTime createdOn;

}

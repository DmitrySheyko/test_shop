package com.example.test_shop.notification.dto;

import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDto {

    private Long id;
    private String text;
    private UserShortDto user;
    private LocalDateTime createdOn;

}

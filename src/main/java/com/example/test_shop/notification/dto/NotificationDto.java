package com.example.test_shop.notification.dto;

import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private Long id;
    private String text;
    private UserShortDto user;

}

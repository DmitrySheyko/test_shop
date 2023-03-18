package com.example.test_shop.notification.service;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;

import java.util.Set;

public interface NotificationService {

    NotificationDto add(NewNotificationDto notificationDto);


    Set<NotificationDto> getAllbyUserId(Long userId);
}

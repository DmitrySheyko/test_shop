package com.example.test_shop.notification.service;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.model.Notification;

import java.util.Set;

/**
 * Interface of service for {@link Notification} entity
 *
 * @author DmitrySheyko
 */
public interface NotificationService {

    NotificationDto add(NewNotificationDto notificationDto);


    Set<NotificationDto> getAllByUserId(Long userId);
}

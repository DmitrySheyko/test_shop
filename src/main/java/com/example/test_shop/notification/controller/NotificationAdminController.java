package com.example.test_shop.notification.controller;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class of admin controller for {@link Notification} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notification")
public class NotificationAdminController {

    private final NotificationService service;

    // Создане уведомления
    @PostMapping
    public NotificationDto add(@Valid @RequestBody NewNotificationDto notificationDto) {
        return service.add(notificationDto);
    }

}

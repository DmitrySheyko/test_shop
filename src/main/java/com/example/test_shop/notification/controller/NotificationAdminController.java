package com.example.test_shop.notification.controller;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // Создание уведомления
    @PostMapping
    public NotificationDto add(@Valid @RequestBody NewNotificationDto notificationDto) {
        return service.add(notificationDto);
    }

    @DeleteMapping("/{notificationId}")
    public String delete(@PathVariable (value = "notificationId") Long notificationId) {
        return service.delete(notificationId);
    }

}

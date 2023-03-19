package com.example.test_shop.notification.controller;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notification")
public class NotificationAdminController {

    private NotificationService service;

    @PostMapping
    public NotificationDto add(@Valid @RequestBody NewNotificationDto notificationDto) {
        return service.add(notificationDto);
    }

}

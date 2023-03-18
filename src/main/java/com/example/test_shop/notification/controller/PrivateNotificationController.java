package com.example.test_shop.notification.controller;

import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.service.NotificationService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/notification")
public class PrivateNotificationController {

    private NotificationService service;

    @GetMapping("/{id}")
    public Set<NotificationDto> getAllbyUserId(@Positive @PathVariable(value = "id") Long userId) {
        return service.getAllbyUserId(userId);
    }
}

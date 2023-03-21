package com.example.test_shop.notification.controller;

import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.notification.service.NotificationService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Class of user's controller for {@link Notification} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/notification")
public class PrivateNotificationController {

    private final NotificationService service;

    // Получение уведомления для пользователя
    @GetMapping
    public Set<NotificationDto> getAllByUserId(@Positive @PathVariable(value = "userId") Long userId) {
        return service.getAllbyUserId(userId);
    }

}

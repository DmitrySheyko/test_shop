package com.example.test_shop.notification.service;

import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations=classpath:data-test.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class NotificationServiceImplTest {
    private final NotificationService service;
    private final NotificationRepository notificationRepository;

    @Test
    void add() {
        Long userId = 3L;
        int quantityOfNotificationsBeforeAdd = notificationRepository.findAll().size();
        NewNotificationDto newNotificationDto = NewNotificationDto.builder()
                .text("Test notification_1")
                .userId(userId)
                .build();
        NotificationDto notificationDto = service.add(newNotificationDto);
        assertEquals(quantityOfNotificationsBeforeAdd + 1, notificationRepository.findAll().size());
        assertEquals("Test notification_1", notificationDto.getText());
    }

    @Test
    void getAllByUserId() {
        assertEquals(0, service.getAllByUserId(1L).size());
        assertEquals(0, service.getAllByUserId(2L).size());
        assertEquals(1, service.getAllByUserId(3L).size());
    }

    @Test
    void delete() {
        Long userId = 3L;
        int quantityOfNotificationsBeforeAdd = notificationRepository.findAll().size();
        NewNotificationDto newNotificationDto = NewNotificationDto.builder()
                .text("Test notification_1")
                .userId(userId)
                .build();
        NotificationDto notificationDto = service.add(newNotificationDto);
        assertEquals(quantityOfNotificationsBeforeAdd + 1, notificationRepository.findAll().size());

        service.delete(notificationDto.getId());
        assertEquals(quantityOfNotificationsBeforeAdd, notificationRepository.findAll().size());
    }

}
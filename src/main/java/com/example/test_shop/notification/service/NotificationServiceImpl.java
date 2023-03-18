package com.example.test_shop.notification.service;

import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.mapper.NotificationMapper;
import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.notification.repository.NotificationRepository;
import com.example.test_shop.user.dto.UserShortDto;
import com.example.test_shop.user.mapper.UserMapper;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;

    @Override
    public NotificationDto add(NewNotificationDto notificationDto) {
        User user = userRepository.findById(notificationDto.getUserId())
                .orElseThrow(() -> new NotFoundException(String
                        .format("Notification didn't add. User id=%s not found", notificationDto.getUserId())));
        Notification newNotification = NotificationMapper.toNotification(notificationDto, user);
        newNotification.setCreatedOn(LocalDateTime.now());
        newNotification = repository.save(newNotification);
        NotificationDto newNotificationDto = NotificationMapper.toDto(newNotification, UserMapper.toUserShortDto(user));
        log.info("Notification id={} successfully add", newNotification.getId());
        return newNotificationDto;
    }

    @Override
    public Set<NotificationDto> getAllbyUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("Notification didn't add. User id=%s not found", userId)));
        Set<Notification> notificationSet = repository.findAllByUser(user);
        UserShortDto userShortDto = UserMapper.toUserShortDto(user);
        Set<NotificationDto> notificationDtoSet = notificationSet.stream()
                .map(notif -> NotificationMapper.toDto(notif, userShortDto))
                .collect(Collectors.toSet());
        log.info("Notifications for user id={} successfully received", userId);
        return notificationDtoSet;
    }

}

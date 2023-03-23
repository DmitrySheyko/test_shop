package com.example.test_shop.notification.service;

import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.notification.dto.NewNotificationDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.notification.mapper.NotificationMapper;
import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.notification.repository.NotificationRepository;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class of service for {@link Notification} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final UserRepository userRepository;

    @Override
    public NotificationDto add(NewNotificationDto notificationDto) {
        User user = userRepository.findById(notificationDto.getUserId())
                .orElseThrow(() -> new NotFoundException(String
                        .format("Notification didn't add. User id=%s not found", notificationDto.getUserId())));
        Notification newNotification = NotificationMapper.toNotification(notificationDto, user);
        newNotification = repository.save(newNotification);
        NotificationDto newNotificationDto = NotificationMapper.toDto(newNotification);
        log.info("Notification id={} successfully add", newNotification.getId());
        return newNotificationDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<NotificationDto> getAllByUserId(Long userId) {
        User user = checkAndGetUser(userId);
        Set<Notification> notificationSet = repository.findAllByUser(user);
        Set<NotificationDto> notificationDtoSet = notificationSet.stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toSet());
        log.info("Notifications for user id={} successfully received", userId);
        return notificationDtoSet;
    }

    private User checkAndGetUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s blocked", userId));
        }
        return user;
    }

}
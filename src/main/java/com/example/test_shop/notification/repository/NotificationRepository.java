package com.example.test_shop.notification.repository;

import com.example.test_shop.notification.model.Notification;
import com.example.test_shop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Interface of repository for {@link Notification} entity
 *
 * @author DmitrySheyko
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Set<Notification> findAllByUser(User user);

}

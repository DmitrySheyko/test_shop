package com.example.test_shop.user.model;

import com.example.test_shop.notification.model.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class o {@link User} entity
 *
 * @author DmitrySheyko
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Notification> notificationSet = new HashSet<>();

    @Column(name = "role")
    private String role;

}
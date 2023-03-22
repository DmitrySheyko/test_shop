package com.example.test_shop.notification.dto;

import com.example.test_shop.notification.model.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Notification} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewNotificationDto {

    @NotBlank(message = "Text can't be blank")
    private String text;

    @Positive(message = "UserId should be positive")
    private Long userId;

}
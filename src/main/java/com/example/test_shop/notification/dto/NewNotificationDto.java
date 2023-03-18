package com.example.test_shop.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewNotificationDto {

    @NotBlank
    private String text;

    @Positive
    private Long userId;

}

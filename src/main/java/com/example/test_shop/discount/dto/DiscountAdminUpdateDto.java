package com.example.test_shop.discount.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DiscountAdminUpdateDto {

    @NotBlank
    private String description;

    @Positive
    private Double value;

    @FutureOrPresent
    private LocalDateTime startDateTime;

    @Future
    private LocalDateTime finishDateTime;

}

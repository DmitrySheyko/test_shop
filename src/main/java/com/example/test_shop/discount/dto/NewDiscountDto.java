package com.example.test_shop.discount.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewDiscountDto {

    @NotBlank
    private String description;

    @Positive
    private Double value;

    @NotNull
    @FutureOrPresent
    private LocalDateTime startDateTime;

    @NotNull
    @Future
    private LocalDateTime finishDateTime;

}

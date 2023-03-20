package com.example.test_shop.discount.dto;

import com.example.test_shop.discount.model.Discount;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for creating new {@link Discount} entity
 *
 * @author DmitrySheyko
 */
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

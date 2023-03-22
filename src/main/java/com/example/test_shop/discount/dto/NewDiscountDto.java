package com.example.test_shop.discount.dto;

import com.example.test_shop.discount.model.Discount;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewDiscountDto {

    @NotBlank(message = "Description can't be null")
    private String description;

    @DecimalMax(value = "1.0", message = "Discount can't be more than 1")
    @DecimalMin(value = "0.001", message = "Discount can't be less than 1" )
    private Double value;

    @NotNull(message = "Start DateTime can't be null")
    private String startDateTime;

    @NotNull(message = "Finish DateTime can't be null")
    private String finishDateTime;

}

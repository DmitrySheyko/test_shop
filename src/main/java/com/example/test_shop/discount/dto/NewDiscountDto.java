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

    @NotBlank
    private String description;

    @DecimalMax("1.0")
    @DecimalMin("0.001")
    private Double value;

    @NotNull
    private String startDateTime;

    @NotNull
    private String finishDateTime;

}

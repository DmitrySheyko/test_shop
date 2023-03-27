package com.example.test_shop.discount.dto;

import com.example.test_shop.discount.model.Discount;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for update {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class DiscountAdminUpdateDto {

    @NotBlank
    private String description;

    @Positive
    private Double value;

    @FutureOrPresent
    private String startDateTime;

    @Future
    private String finishDateTime;

}
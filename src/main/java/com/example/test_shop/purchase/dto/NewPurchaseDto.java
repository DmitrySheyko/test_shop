package com.example.test_shop.purchase.dto;

import com.example.test_shop.purchase.model.Purchase;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewPurchaseDto {

    @NotNull
    @Positive
    private Long companyId;

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Double priceForUnit;

}

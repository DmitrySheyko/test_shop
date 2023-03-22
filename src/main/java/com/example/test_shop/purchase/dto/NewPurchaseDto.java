package com.example.test_shop.purchase.dto;

import com.example.test_shop.purchase.model.Purchase;
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

    @Positive(message = "CompanyId should be positive")
    private Long companyId;

    @Positive(message = "ProductId should be positive")
    private Long productId;

    @Positive(message = "Quantity should be positive")
    private Integer quantity;

    @Positive(message = "Price for unit should be positive")
    private Double priceForUnit;

}

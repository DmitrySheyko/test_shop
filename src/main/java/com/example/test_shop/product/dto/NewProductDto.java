package com.example.test_shop.product.dto;

import com.example.test_shop.product.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewProductDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Long companyId;

    @NotNull
    private Double price;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
    private Long discountId;
    private String keyWords;
    private String characteristics;

}

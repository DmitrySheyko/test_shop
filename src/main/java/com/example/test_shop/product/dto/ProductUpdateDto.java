package com.example.test_shop.product.dto;

import com.example.test_shop.product.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for update information about {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class ProductUpdateDto {

    @NotNull
    @Positive
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Long discountId;
    private String keyWords;
    private String characteristics;
    private String status;

}
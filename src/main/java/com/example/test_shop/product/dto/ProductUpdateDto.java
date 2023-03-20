package com.example.test_shop.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

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

}
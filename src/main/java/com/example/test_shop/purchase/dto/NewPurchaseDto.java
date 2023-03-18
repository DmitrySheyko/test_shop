package com.example.test_shop.purchase.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewPurchaseDto {

    @NotNull
    @Positive
    private Long company;

    @NotNull
    @Positive
    private Long product;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Double priceForUnit;

}

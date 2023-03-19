package com.example.test_shop.discount.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountShortDto {

    private Long id;
    private String description;
    private Double value;

}

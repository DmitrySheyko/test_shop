package com.example.test_shop.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductShortDto {

    private Long id;
    private String name;

}

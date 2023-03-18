package com.example.test_shop.product.dto;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class ProductShortDto {

    private Long id;
    private String name;

}

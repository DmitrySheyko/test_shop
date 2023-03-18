package com.example.test_shop.product.mapper;

import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductShortDto toShortDto(Product product) {
        if (product == null) {
            return null;
        } else {
            return ProductShortDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .build();
        }
    }

}

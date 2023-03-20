package com.example.test_shop.product.dto;

import com.example.test_shop.product.model.Product;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for getting short information about {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class ProductShortDto {

    private Long id;
    private String name;

}

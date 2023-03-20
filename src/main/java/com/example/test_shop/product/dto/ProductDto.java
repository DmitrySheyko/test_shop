package com.example.test_shop.product.dto;

import com.example.test_shop.company.dto.CompanyShortDto;
import com.example.test_shop.discount.dto.DiscountShortDto;
import com.example.test_shop.product.model.Product;
import jakarta.validation.constraints.Digits;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for getting full information about {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private CompanyShortDto company;
    private Double price;
    private Integer quantity;
    private DiscountShortDto discount;
    private String keyWords;
    private String characteristics;

    @Digits(integer = 1, fraction = 1)
    private String rates;

}

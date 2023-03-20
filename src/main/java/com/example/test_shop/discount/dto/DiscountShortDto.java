package com.example.test_shop.discount.dto;

import com.example.test_shop.discount.model.Discount;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for getting short information about {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class DiscountShortDto {

    private Long id;
    private String description;
    private Double value;

}

package com.example.test_shop.discount.dto;

import com.example.test_shop.discount.model.Discount;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for getting full information about {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class DiscountDto {

    private Long id;
    private String description;
    private Double value;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

}

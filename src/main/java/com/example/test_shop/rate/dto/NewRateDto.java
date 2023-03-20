package com.example.test_shop.rate.dto;

import com.example.test_shop.rate.model.Rate;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Rate}
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewRateDto {

    @PositiveOrZero
    private Integer rate;

    @Positive
    private Long productId;

}

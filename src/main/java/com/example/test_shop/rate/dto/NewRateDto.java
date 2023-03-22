package com.example.test_shop.rate.dto;

import com.example.test_shop.rate.model.Rate;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
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

    @Min(value = 0, message = "ProductId should be from 0 to 5")
    @Max(value = 5, message = "ProductId should be from 0 to 5")
    private Integer rate;

    @Positive(message = "ProductId can't be less than 1")
    private Long productId;

}
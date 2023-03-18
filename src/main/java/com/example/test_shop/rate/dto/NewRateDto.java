package com.example.test_shop.rate.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewRateDto {

    @PositiveOrZero
    private Integer rate;

    @Positive
    private Long productId;

}

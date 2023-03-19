package com.example.test_shop.discount.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DiscountDto {

    private Long id;
    private String description;
    private Double value;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

}

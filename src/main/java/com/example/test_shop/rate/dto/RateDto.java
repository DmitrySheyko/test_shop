package com.example.test_shop.rate.dto;

import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.rate.model.Rate;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for getting information about {@link Rate}
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class RateDto {

    private Long id;
    private Integer rate;
    private UserShortDto user;
    private ProductShortDto product;
    private LocalDateTime createdOn;

}

package com.example.test_shop.rate.mapper;

import com.example.test_shop.product.mapper.ProductMapper;
import com.example.test_shop.rate.dto.NewRateDto;
import com.example.test_shop.rate.dto.RateDto;
import com.example.test_shop.rate.model.Rate;
import com.example.test_shop.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Class of mapper for {@link Rate}
 *
 * @author DmitrySheyko
 */
@Component
public class RateMapper {

    public static Rate toRate(NewRateDto rateDto) {
        if (rateDto == null) {
            return null;
        } else {
            return Rate.builder()
                    .rate(rateDto.getRate())
                    .build();
        }
    }

    public static RateDto toDto(Rate rate) {
        if (rate == null) {
            return null;
        } else {
            return RateDto.builder()
                    .id(rate.getId())
                    .rate(rate.getRate())
                    .user(UserMapper.toUserShortDto(rate.getUser()))
                    .product(ProductMapper.toShortDto(rate.getProduct()))
                    .createdOn(rate.getCreatedOn())
                    .build();
        }
    }

}

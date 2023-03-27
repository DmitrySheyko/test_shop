package com.example.test_shop.discount.mapper;

import com.example.test_shop.discount.dto.DiscountAdminUpdateDto;
import com.example.test_shop.discount.dto.DiscountDto;
import com.example.test_shop.discount.dto.DiscountShortDto;
import com.example.test_shop.discount.dto.NewDiscountDto;
import com.example.test_shop.discount.model.Discount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class of mapper for {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@Component
public class DiscountMapper {

    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Discount toDiscount(NewDiscountDto discountDto) {
        if (discountDto == null) {
            return null;
        } else {
            return Discount.builder()
                    .description(discountDto.getDescription())
                    .value(discountDto.getValue())
                    .startDateTime(toDateTime(discountDto.getStartDateTime()))
                    .finishDateTime(toDateTime(discountDto.getFinishDateTime()))
                    .build();
        }
    }

    private static LocalDateTime toDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_PATTERN);
    }

    public static Discount toDiscount(DiscountAdminUpdateDto discountDto) {
        if (discountDto == null) {
            return null;
        } else {
            return Discount.builder()
                    .description(discountDto.getDescription())
                    .value(discountDto.getValue())
                    .startDateTime(toDateTime(discountDto.getStartDateTime()))
                    .finishDateTime(toDateTime(discountDto.getFinishDateTime()))
                    .build();
        }
    }

    public static DiscountDto toDiscountDto(Discount discount) {
        if (discount == null) {
            return null;
        } else {
            return DiscountDto.builder()
                    .id(discount.getId())
                    .description(discount.getDescription())
                    .value(discount.getValue())
                    .startDateTime(discount.getStartDateTime())
                    .finishDateTime(discount.getFinishDateTime())
                    .build();
        }
    }

    public static DiscountShortDto toDiscountShortDto(Discount discount) {
        if (discount == null) {
            return null;
        } else {
            return DiscountShortDto.builder()
                    .id(discount.getId())
                    .description(discount.getDescription())
                    .value(discount.getValue())
                    .build();
        }
    }

}

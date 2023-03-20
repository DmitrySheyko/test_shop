package com.example.test_shop.product.mapper;

import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.discount.mapper.DiscountMapper;
import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.product.dto.ProductUpdateDto;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.rate.model.Rate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Class of mapper for {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Component
public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        } else {
            return ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .company(CompanyMapper.toShortDto(product.getCompany()))
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .discount(DiscountMapper.toDiscountShortDto(product.getDiscount()))
                    .keyWords(product.getKeyWords())
                    .characteristics(product.getCharacteristics())
                    .rates(calculateRate(product.getRatesSet()))
                    .build();
        }
    }

    public static Product toDiscount(ProductUpdateDto productDto) {
        if (productDto == null) {
            return null;
        } else {
            return Product.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .description(productDto.getDescription())
                    .price(productDto.getPrice())
                    .quantity(productDto.getQuantity())
                    .keyWords(productDto.getKeyWords())
                    .characteristics(productDto.getCharacteristics())
                    .build();
        }
    }

    public static ProductShortDto toShortDto(Product product) {
        if (product == null) {
            return null;
        } else {
            return ProductShortDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .build();
        }
    }

    // Метод расчитывает и возвращает среднее арифметическое оценок
    private static String calculateRate(Set<Rate> ratesSet) {
        if (ratesSet.isEmpty()) {
            return null;
        }
        Float sum = 0F;
        for (Rate rate : ratesSet) {
            sum += (float) rate.getRate();
        }
        return String.format("%.1f", sum / (float) ratesSet.size());
    }

}

package com.example.test_shop.product.mapper;

import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.discount.mapper.DiscountMapper;
import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.product.dto.ProductUpdateDto;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.model.ProductStatus;
import com.example.test_shop.rate.model.Rate;
import org.springframework.stereotype.Component;

import java.util.List;

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
                    .rates(calculateRate(product.getRatesList()))
                    .satus(product.getStatus().name())
                    .build();
        }
    }

    public static Product toProduct(ProductUpdateDto productDto) {
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
                    .status(productDto.getStatus() != null
                            ? ProductStatus.valueOf(productDto.getStatus())
                            : null)
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
                    .status(product.getStatus().name())
                    .build();
        }
    }

    // Метод расчитывает и возвращает среднее арифметическое оценок
    private static String calculateRate(List<Rate> rateList) {
        if (rateList.isEmpty()) {
            return null;
        }
        float sum = 0F;
        for (Rate rate : rateList) {
            sum += (float) rate.getRate();
        }
        return String.format("%.1f", sum / (float) rateList.size());
    }

}

package com.example.test_shop.product.dto;

import com.example.test_shop.product.model.Product;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewProductDto {

    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotBlank(message = "Description can't be blank")
    private String description;

    @Positive(message = "CompanyId should be positive")
    private Long companyId;

    @DecimalMin(value = "0.01", message = "Price can't be less than 0.01" )
    @Digits(integer = 9, fraction = 2)
    private Double price;

    @PositiveOrZero(message = "Quantity can't be less than null")
    private Integer quantity;
    private Long discountId;
    private String keyWords;
    private String characteristics;

}

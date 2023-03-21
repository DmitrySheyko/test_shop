package com.example.test_shop.purchase.dto;

import com.example.test_shop.company.dto.CompanyShortDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for buyers getting information about {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class PurchaseBuyerDto {

    private Long id;
    private String type;
    private CompanyShortDto sellCompany;
    private UserShortDto buyer;
    private ProductShortDto product;
    private Integer quantity;
    private Double priceForUnit;
    private Double totalSum;
    private LocalDateTime purchaseDateTime;

}

package com.example.test_shop.purchase.dto;

import com.example.test_shop.company.dto.CompanyShortDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for getting full information about {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class PurchaseDto {

    private Long id;
    private String type;
    private CompanyShortDto company;
    private String companyName;
    private Long companyId;
    private UserShortDto seller;
    private UserShortDto buyer;
    private ProductShortDto product;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double priceForUnit;
    private Double totalSumWithoutDiscount;
    private Double totalSumWithDiscount;
    private Double shopCommissionSum;
    private Double discountSum;
    private Double sellerIncomeSum;
    private LocalDateTime purchaseDateTime;
    private Boolean isRejected;
    private Long rejectionId;

}
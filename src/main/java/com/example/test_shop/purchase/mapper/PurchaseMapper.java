package com.example.test_shop.purchase.mapper;

import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.product.mapper.ProductMapper;
import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.PurchaseDto;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Class of mapper for {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
@Component
public class PurchaseMapper {

    public static PurchaseBuyerDto toPurchaseBuyerDto(Purchase purchase) {
        if (purchase == null) {
            return null;
        } else {
            return PurchaseBuyerDto.builder()
                    .id(purchase.getId())
                    .type(purchase.getType().name())
                    .sellCompany(CompanyMapper.toShortDto(purchase.getCompany()))
                    .buyer(UserMapper.toUserShortDto(purchase.getBuyer()))
                    .product(ProductMapper.toShortDto(purchase.getProduct()))
                    .quantity(purchase.getQuantity())
                    .priceForUnit(purchase.getPriceForUnit())
                    .totalSumWithoutDiscount(purchase.getTotalSumWithoutDiscount())
                    .totalSumWithDiscount(purchase.getTotalSumWithDiscount())
                    .discountSum(purchase.getDiscountSum())
                    .purchaseDateTime(purchase.getPurchaseDateTime())
                    .build();
        }
    }

    public static PurchaseDto toPurchaseDto(Purchase purchase) {
        if (purchase == null) {
            return null;
        } else {
            return PurchaseDto.builder()
                    .id(purchase.getId())
                    .type(purchase.getType().name())
                    .company(CompanyMapper.toShortDto(purchase.getCompany()))
                    .seller(UserMapper.toUserShortDto(purchase.getSeller()))
                    .buyer(UserMapper.toUserShortDto(purchase.getBuyer()))
                    .product(ProductMapper.toShortDto(purchase.getProduct()))
                    .quantity(purchase.getQuantity())
                    .priceForUnit(purchase.getPriceForUnit())
                    .totalSumWithoutDiscount(purchase.getTotalSumWithoutDiscount())
                    .totalSumWithDiscount(purchase.getTotalSumWithDiscount())
                    .shopCommissionSum(purchase.getShopCommissionSum())
                    .discountSum(purchase.getDiscountSum())
                    .sellerIncomeSum(purchase.getSellerIncomeSum())
                    .purchaseDateTime(purchase.getPurchaseDateTime())
                    .build();
        }
    }

}
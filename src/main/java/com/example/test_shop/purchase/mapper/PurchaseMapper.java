package com.example.test_shop.purchase.mapper;

import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.product.mapper.ProductMapper;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.user.mapper.UserMapper;
import com.example.test_shop.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {

    public static Purchase toPurchase(NewPurchaseDto purchaseDto, Company company, User buyer, User seller,
                                      Product product, Double totalSum, Double shopComission) {
        if (purchaseDto == null) {
            return null;

        } else {
            return Purchase.builder()
                    .company(company)
                    .seller(seller)
                    .buyer(buyer)
                    .product(product)
                    .quantity(purchaseDto.getQuantity())
                    .priceForUnit(purchaseDto.getPriceForUnit())
                    .totalSum(totalSum)
                    .shopComission(shopComission)
                    .build();
        }
    }

    public static PurchaseBuyerDto toBuyerPurchaseDto(Purchase purchase) {
        if (purchase == null) {
            return null;
        } else {
            return PurchaseBuyerDto.builder()
                    .id(purchase.getId())
                    .sellCompany(CompanyMapper.toShortDto(purchase.getCompany()))
                    .buyer(UserMapper.toUserShortDto(purchase.getBuyer()))
                    .product(ProductMapper.toShortDto(purchase.getProduct()))
                    .quantity(purchase.getQuantity())
                    .priceForUnit(purchase.getPriceForUnit())
                    .totalSum(purchase.getTotalSum())
                    .purchaseDateTime(purchase.getPurchaseDateTime())
                    .build();
        }
    }
}
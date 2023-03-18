package com.example.test_shop.purchase.service;

import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;

import java.util.Set;

public interface PurchaseService {

    PurchaseBuyerDto add(NewPurchaseDto purchaseDto, Long userId);

    Set<PurchaseBuyerDto> getAllOwnPurchases(Long buyerId);
}

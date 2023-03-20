package com.example.test_shop.purchase.service;

import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.model.Purchase;

import java.util.Set;

/**
 * Interface of service for {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
public interface PurchaseService {

    PurchaseBuyerDto add(NewPurchaseDto purchaseDto, Long userId);

    Set<PurchaseBuyerDto> getAllOwnPurchases(Long buyerId);

    PurchaseBuyerDto reject(Long buyerId, Long purchaseId);

}

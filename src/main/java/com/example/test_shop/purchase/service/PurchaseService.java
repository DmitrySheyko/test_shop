package com.example.test_shop.purchase.service;

import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.dto.PurchaseDto;
import com.example.test_shop.purchase.dto.RejectionDto;
import com.example.test_shop.purchase.model.Purchase;

import java.util.List;

/**
 * Interface of service for {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
public interface PurchaseService {

    PurchaseBuyerDto add(NewPurchaseDto purchaseDto, Long userId);

    List<PurchaseBuyerDto> getAllOwnPurchases(Long buyerId);

    RejectionDto reject(Long buyerId, Long purchaseId);

    List<PurchaseDto> getAllOwnSales(Long buyerId);
}

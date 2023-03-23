package com.example.test_shop.purchase.controller;

import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.dto.PurchaseDto;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Class user's controller for {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/purchase")
public class PurchasePrivateController {
    private final PurchaseService service;

    // Создать новую поккупку (приобрести товар)
    @PostMapping
    public PurchaseBuyerDto add(@Valid @RequestBody NewPurchaseDto purchaseDto,
                                @Positive @PathVariable(value = "userId") Long buyerId) {
        return service.add(purchaseDto, buyerId);
    }

    // Получить список своих покупок
    @GetMapping("/purchases")
    public Set<PurchaseBuyerDto> getAllOwnPurchases(@Positive @PathVariable(value = "userId") Long buyerId) {
        return service.getAllOwnPurchases(buyerId);
    }

    // Получить список своих продаж
    @GetMapping("/sales")
    public Set<PurchaseDto> getAllOwnSales(@Positive @PathVariable(value = "userId") Long buyerId) {
        return service.getAllOwnSales(buyerId);
    }

    // Отказаться от покупки в течение суток после покупки
    @PostMapping("/{purchaseId}")
    public PurchaseBuyerDto reject(@Positive @PathVariable(value = "userId") Long buyerId,
                                   @Positive @PathVariable(value = "purchaseId") Long purchaseId) {
        return service.reject(buyerId, purchaseId);
    }

}

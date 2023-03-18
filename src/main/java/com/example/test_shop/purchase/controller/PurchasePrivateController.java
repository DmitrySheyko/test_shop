package com.example.test_shop.purchase.controller;

import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/purchase")
public class PurchasePrivateController {
    private final PurchaseService service;

    @PostMapping("/{id}")
    public PurchaseBuyerDto add(@Valid NewPurchaseDto purchaseDto,
                                @Positive @PathVariable(value = "id") Long buyerId){
        return service.add(purchaseDto, buyerId);
    }

    @GetMapping("/{id}")
    public Set<PurchaseBuyerDto> getAllOwnPurchases(@Positive @PathVariable(value = "id") Long buyerId){
        return service.getAllOwnPurchases(buyerId);
    }

}

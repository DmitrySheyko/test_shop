package com.example.test_shop.purchase.service;

import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.RejectionDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations = classpath:data-test.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PurchaseServiceImplTest {

    private final PurchaseService purchaseService;

    @Test
    void add() {
        NewPurchaseDto newPurchaseDto = NewPurchaseDto.builder()
                .companyId(1L)
                .productId(1L)
                .quantity(3)
                .priceForUnit(1000.0)
                .build();
        PurchaseBuyerDto purchaseDto = purchaseService.add(newPurchaseDto, 3L);
        assertEquals("PURCHASE", purchaseDto.getType());
        assertEquals(3, purchaseDto.getBuyer().getId());
        assertEquals(1, purchaseDto.getProduct().getId());
        assertEquals(3, purchaseDto.getQuantity());
        assertEquals(1000.00, purchaseDto.getPriceForUnit());
        assertEquals(3000.00, purchaseDto.getTotalSumWithoutDiscount());
        assertEquals(2700.00, purchaseDto.getTotalSumWithDiscount());
        assertEquals(300.00, purchaseDto.getDiscountSum());
        assertNotNull(purchaseDto.getPurchaseDateTime());
    }

    @Test
    void getAllOwnPurchases() {
        Long buyer = 3L;
        int quantityOfPurchases = 3;
        List<PurchaseBuyerDto> purchaseDtoList = purchaseService.getAllOwnPurchases(buyer);
        assertEquals(quantityOfPurchases, purchaseDtoList.size());
    }

    @Test
    void reject() {
        Long buyerId = 3L;

        NewPurchaseDto newPurchaseDto = NewPurchaseDto.builder()
                .companyId(1L)
                .productId(1L)
                .quantity(1)
                .priceForUnit(1000.0)
                .build();
        PurchaseBuyerDto purchaseDto = purchaseService.add(newPurchaseDto, 3L);

        RejectionDto rejectionDto = purchaseService.reject(buyerId, purchaseDto.getId());

        assertEquals("REJECT", rejectionDto.getType());
        assertEquals(1, rejectionDto.getCompany().getId());
        assertEquals(3, rejectionDto.getBuyer().getId());
        assertEquals(4, rejectionDto.getRejectForPurchaseId());
        assertEquals(1, rejectionDto.getQuantity());
        assertEquals(1000.0, rejectionDto.getPriceForUnit());
        assertEquals(1000.0, rejectionDto.getTotalSumWithoutDiscount());
        assertEquals(900.0, rejectionDto.getTotalSumWithDiscount());
        assertEquals(45.0, rejectionDto.getShopCommissionSum());
        assertEquals(100.0, rejectionDto.getDiscountSum());
        assertEquals(855.0, rejectionDto.getSellerIncomeSum());
        assertNotNull(rejectionDto.getPurchaseDateTime());
        assertEquals(4, rejectionDto.getRejectForPurchaseId());
    }

    @Test
    void getAllOwnSales() {
        Long seller = 2L;
        int quantityOfSales = 3;
        assertEquals(quantityOfSales, purchaseService.getAllOwnSales(seller).size());
    }
}
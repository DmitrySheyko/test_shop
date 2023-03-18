package com.example.test_shop.purchase.service;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.company.repository.CompanyRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.product.repository.ProductRepository;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.mapper.PurchaseMapper;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.purchase.repository.PurchaseRepository;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private static final float COMMISSION_OF_SHOP = 0.05F;

    private final PurchaseRepository repository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;


    @Override
    public PurchaseBuyerDto add(NewPurchaseDto purchaseDto, Long userId) {
        User buyer = checkAndGetBuyer(userId, purchaseDto.getPriceForUnit(), purchaseDto.getQuantity());
        Company sellCompany = checkAndGetCompany(purchaseDto.getCompany());
        User seller = checkAndGetSeller(sellCompany.getOwner().getId());
        Product product = checkAndGetProduct(purchaseDto.getProduct(), purchaseDto.getPriceForUnit(),
                purchaseDto.getQuantity());

        Double totalSum = purchaseDto.getPriceForUnit() * purchaseDto.getQuantity();
        double shopCommissionSum = totalSum * COMMISSION_OF_SHOP;

        // Вычитаем купленный товар из складских запасов
        product.setQuantity(product.getQuantity() - purchaseDto.getQuantity());
        productRepository.save(product);

        // Вычитаем стоимость покупки из баланса покупателя
        buyer.setBalance(buyer.getBalance() - totalSum);
        userRepository.save(buyer);

        // Зачисляем на счет продавца стоимость покупки за вычетом комиссии 5%
        seller.setBalance(seller.getBalance() + totalSum - shopCommissionSum);
        userRepository.save(seller);

        // Сохраняем данные о покупке
        Purchase purchase = PurchaseMapper.toPurchase(purchaseDto, sellCompany, buyer, seller, product, totalSum,
                shopCommissionSum);
        purchase.setPurchaseDateTime(LocalDateTime.now());
        purchase = repository.save(purchase);

        // Возвращаем BuyerPurchaseDto
        PurchaseBuyerDto purchaseBuyerDto = PurchaseMapper.toBuyerPurchaseDto(purchase);
        log.info("Purchase id={} successfully saved", purchase.getId());
        return purchaseBuyerDto;
    }

    @Override
    public Set<PurchaseBuyerDto> getAllOwnPurchases(Long buyerId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", buyerId)));
        Set<Purchase> purchasesSet = repository.findAllByBuyer(buyer);
        Set<PurchaseBuyerDto> purchaseBuyerDtoSet = purchasesSet.stream()
                .map(PurchaseMapper::toBuyerPurchaseDto)
                .collect(Collectors.toSet());
        log.info("Set of purchases of user id={} successfully saved", buyerId);
        return purchaseBuyerDtoSet;
    }


    private User checkAndGetBuyer(Long buyerId, Double priceForUnit, Integer quantity) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", buyerId)));
        if (buyer.getStatus().equals(UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s blocked", buyerId));
        }
        if (buyer.getBalance() < priceForUnit * quantity) {
            throw new ValidationException(String.format("User id=%s has low balance: %s, required: %s",
                    buyerId, buyer.getBalance(), priceForUnit * quantity));
        }
        return buyer;
    }

    private User checkAndGetSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", sellerId)));
        if (seller.getStatus().equals(UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s blocked", sellerId));
        }
        return seller;
    }


    private Company checkAndGetCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).
                orElseThrow(() -> new NotFoundException(String.format("Company id=%s not found", companyId)));
        if (company.getStatus().equals(CompanyStatus.BLOCKED)) {
            throw new ValidationException(String.format("Company id=%s blocked", companyId));
        }
        if (company.getStatus().equals(CompanyStatus.PENDING)) {
            throw new ValidationException(String.format("Company id=%s doesn't activated yet", companyId));
        }

        return company;
    }

    private Product checkAndGetProduct(Long productId, Double priceForUnit, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product id=%s not found", productId)));
        if (product.getQuantity() < quantity) {
            throw new ValidationException(String.format("Quantity of product id=%s in store: %s, required: %s",
                    productId, product.getQuantity(), quantity));
        }
        if (!Objects.equals(product.getPrice(), priceForUnit)) {
            throw new ValidationException(String.format("Price for unit of product id=%s is: %s, required: %s",
                    productId, product.getPrice(), priceForUnit));
        }
        return product;
    }

}

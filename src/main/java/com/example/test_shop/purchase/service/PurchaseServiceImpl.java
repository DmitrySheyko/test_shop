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
import com.example.test_shop.purchase.model.PurchaseType;
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

/**
 * Class of service for {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
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
        User buyer = checkAndGetUser(userId);
        if (buyer.getBalance() < purchaseDto.getPriceForUnit() * purchaseDto.getQuantity()) {
            throw new ValidationException(String.format("User id=%s has low balance: %s, required: %s",
                    buyer.getId(), buyer.getBalance(), purchaseDto.getPriceForUnit() * purchaseDto.getQuantity()));
        }
        Company sellCompany = checkAndGetCompany(purchaseDto.getCompany());
        User seller = checkAndGetUser(sellCompany.getOwner().getId());
        Product product = checkAndGetProduct(purchaseDto.getProduct(), purchaseDto.getPriceForUnit(),
                purchaseDto.getQuantity());

        // Рассчитываем сумму покупки у учетом скидки
        Double totalSum = product.getPrice() * purchaseDto.getQuantity() * product.getDiscount().getValue();
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
        purchase.setType(PurchaseType.PURCHASE);
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

    @Override
    public PurchaseBuyerDto reject(Long buyerId, Long purchaseId) {
        // Проверяем, существует и не заблокирован ли покупатель
        User buyer = checkAndGetUser(buyerId);

        // Проверяем, существует и не заблокирован ли продавец
        User seller = checkAndGetUser(buyerId);

        // Проверяем сущесвует ли покупка
        Purchase purchase = repository.findById(purchaseId)
                .orElseThrow(() -> new NotFoundException(String.format("Purchase id=%s not found", purchaseId)));

        // Проверяемне прошли ли 24 часа с момента покупки
        if(purchase.getPurchaseDateTime().plusDays(1).isBefore(LocalDateTime.now())){
            throw new ValidationException(String.format("Product id=%s was purchased more than 24 hour ago", purchaseId));
        }

        // Осуществляем возврат денег покупателю
        seller.setBalance(seller.getBalance() - purchase.getTotalSum() - purchase.getShopCommission());
        buyer.setBalance(buyer.getBalance() + purchase.getTotalSum());
        userRepository.save(seller);
        userRepository.save(buyer);

        // Осуществляем возврат товара компании продавци
        Product product = purchase.getProduct();
        product.setQuantity(purchase.getQuantity() + purchase.getQuantity());
        productRepository.save(product);

        // Создаем и сохраняем новый Purchase со статусом Reject
        Purchase reject = Purchase.builder()
                .type(PurchaseType.REJECT)
                .company(product.getCompany())
                .seller(purchase.getSeller())
                .buyer(purchase.getBuyer())
                .product(purchase.getProduct())
                .priceForUnit(purchase.getPriceForUnit())
                .totalSum(purchase.getTotalSum())
                .shopCommission(purchase.getShopCommission())
                .purchaseDateTime(LocalDateTime.now())
                .build();

        PurchaseBuyerDto rejectDto = PurchaseMapper.toBuyerPurchaseDto(reject);
        log.info("Reject id={} for purchases id={} successfully created", reject.getId(), purchase.getId());
        return rejectDto;
    }


    private User checkAndGetUser(Long userId) {
        User buyer = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        if (buyer.getStatus().equals(UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s blocked", userId));
        }
        return buyer;
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

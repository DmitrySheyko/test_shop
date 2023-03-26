package com.example.test_shop.purchase.service;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.company.repository.CompanyRepository;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.product.model.ProductStatus;
import com.example.test_shop.product.repository.ProductRepository;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.configuration.AppProperties;
import com.example.test_shop.purchase.dto.PurchaseBuyerDto;
import com.example.test_shop.purchase.dto.NewPurchaseDto;
import com.example.test_shop.purchase.dto.PurchaseDto;
import com.example.test_shop.purchase.dto.RejectionDto;
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
import java.util.List;
import java.util.Objects;
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

    private final AppProperties properties;
    private final PurchaseRepository repository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    @Override
    public PurchaseBuyerDto add(NewPurchaseDto purchaseDto, Long userId) {

        // Проверяем, существует и не заблокирован ли покупатель
        User buyer = checkAndGetUser(userId);

        // Проверяем, существует и не заблокирована ли компания
        Company sellCompany = checkAndGetCompany(purchaseDto.getCompanyId());

        // Проверяем, существует и не заблокирован ли продавец
        User seller = checkAndGetUser(sellCompany.getOwner().getId());

        // Проверяем существует ли продукт, активен ли он, в достаточном ли он количестве и совпадает ли цена с уеной из базы
        Product product = checkAndGetProduct(purchaseDto.getProductId(), purchaseDto.getPriceForUnit(),
                purchaseDto.getQuantity());

        // Проверяем достаточно ли у покупателя денег для покупки
        if (buyer.getBalance() < purchaseDto.getPriceForUnit() * purchaseDto.getQuantity()) {
            throw new ValidationException(String.format("User id=%s has low balance: %s, required: %s",
                    buyer.getId(), buyer.getBalance(), purchaseDto.getPriceForUnit() * purchaseDto.getQuantity()));
        }

        // Рассчитываем сумму покупки без учета скидки
        Double totalSumWithoutDiscount = product.getPrice() * purchaseDto.getQuantity();

        // Проверяем действительна ли скидка и расчитываем сумму скидки
        double discountSum;
        Discount discount = product.getDiscount();
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (discount != null && (currentDateTime.isAfter(discount.getStartDateTime()) ||
                currentDateTime.isBefore(discount.getFinishDateTime()))) {
            discountSum = totalSumWithoutDiscount * discount.getValue();
        } else {
            discountSum = 0.0;
        }

        // Рассчитываем сумму покупки с учетщм скидки
        Double totalSumWithDiscount = totalSumWithoutDiscount - discountSum;

        // Рассчитываем сумму комиссии магазина
        Double shopCommissionSum = totalSumWithDiscount * properties.getCommissionOfShop();

        // Рассчитываем сумму выручки продавца
        Double sellerIncomeSum = totalSumWithDiscount - shopCommissionSum;

        // Вычитаем купленный товар из складских запасов
        product.setQuantity(product.getQuantity() - purchaseDto.getQuantity());
        productRepository.save(product);

        // Вычитаем стоимость покупки из баланса покупателя
        buyer.setBalance(buyer.getBalance() - totalSumWithDiscount);
        userRepository.save(buyer);

        // Зачисляем на счет продавца стоимость покупки за вычетом комиссии
        seller.setBalance(seller.getBalance() + sellerIncomeSum);
        userRepository.save(seller);

        // Сохраняем данные о покупке
        Purchase purchase = Purchase.builder()
                .type(PurchaseType.PURCHASE)
                .company(sellCompany)
                // Для сохранения информации при в случае удаления компании из базы
                .companyName(sellCompany.getName())
                // Для сохранения информации при в случае удаления компании из базы
                .companyId(sellCompany.getId())
                .seller(seller)
                .buyer(buyer)
                .product(product)
                // Для сохранения информации при в случае удаления товара из базы
                .productId(product.getId())
                // Для сохранения информации при в случае удаления товара из базы
                .productName(product.getName())
                .quantity(purchaseDto.getQuantity())
                .priceForUnit(purchaseDto.getPriceForUnit())
                .totalSumWithoutDiscount(totalSumWithoutDiscount)
                .totalSumWithDiscount(totalSumWithDiscount)
                .shopCommissionSum(shopCommissionSum)
                .discountSum(discountSum)
                .sellerIncomeSum(sellerIncomeSum)
                .build();
        purchase = repository.save(purchase);

        // Возвращаем результат
        PurchaseBuyerDto purchaseBuyerDto = PurchaseMapper.toPurchaseBuyerDto(purchase);
        log.info("Purchase id={} successfully saved", purchase.getId());
        return purchaseBuyerDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseBuyerDto> getAllOwnPurchases(Long buyerId) {
        User buyer = checkAndGetUser(buyerId);

        // Получаем список покупок по пользователю
        List<Purchase> purchasesSet = repository.findAllByBuyer(buyer);

        // Возвращаем результат
        List<PurchaseBuyerDto> purchaseBuyerDtoSet = purchasesSet.stream()
                .map(PurchaseMapper::toPurchaseBuyerDto)
                .collect(Collectors.toList());
        log.info("Set of purchases of user id={} successfully saved", buyerId);
        return purchaseBuyerDtoSet;
    }

    @Override
    public RejectionDto reject(Long buyerId, Long purchaseId) {
        // Проверяем, существует и не заблокирован ли покупатель
        User buyer = checkAndGetUser(buyerId);

        // Проверяем, существует и не заблокирован ли продавец
        User seller = checkAndGetUser(buyerId);

        // Проверяем сущесвует ли покупка
        Purchase purchase = repository.findById(purchaseId)
                .orElseThrow(() -> new NotFoundException(String.format("Purchase id=%s not found", purchaseId)));


        // Проверяемне прошел ли 1 день с момента покупки
        if (purchase.getPurchaseDateTime().plusDays(properties.getDaysForReturnProducts()).isBefore(LocalDateTime.now())) {
            throw new ValidationException(String.format("Product id=%s was purchased more than %s day ago", purchaseId,
                    properties.getDaysForReturnProducts()));
        }

        // Проверяем не было ли возврата ранее
        if (purchase.isRejected()) {
            throw new ValidationException(String.format("Rejection for purchase id=%s already made. Rejection id=%s",
                    purchase.getId(), purchase.getRejectionId()));
        }

        // Списываем сумму с продавца и осуществляем возврат денег покупателю
        seller.setBalance(seller.getBalance() - purchase.getSellerIncomeSum());
        buyer.setBalance(buyer.getBalance() + purchase.getTotalSumWithDiscount());
        userRepository.save(seller);
        userRepository.save(buyer);

        // Осуществляем возврат товара компании продавца
        Product product = purchase.getProduct();
        product.setQuantity(product.getQuantity() + purchase.getQuantity());
        productRepository.save(product);

        // Создаем и сохраняем новый Purchase со статусом Reject
        Purchase reject = Purchase.builder()
                .type(PurchaseType.REJECT)
                .company(purchase.getCompany())
                // Для сохранения информации при в случае удаления компании из базы
                .companyName(purchase.getCompanyName())
                // Для сохранения информации при в случае удаления компании из базы
                .companyId(purchase.getCompanyId())
                .seller(purchase.getSeller())
                .buyer(purchase.getBuyer())
                .product(purchase.getProduct())
                // Для сохранения информации при в случае удаления товара из базы
                .productId(purchase.getProduct().getId())
                // Для сохранения информации при в случае удаления товара из базы
                .productName(purchase.getProduct().getName())
                .quantity(purchase.getQuantity())
                .priceForUnit(purchase.getPriceForUnit())
                .totalSumWithoutDiscount(purchase.getTotalSumWithoutDiscount())
                .totalSumWithDiscount(purchase.getTotalSumWithDiscount())
                .shopCommissionSum(purchase.getShopCommissionSum())
                .discountSum(purchase.getDiscountSum())
                .sellerIncomeSum(purchase.getSellerIncomeSum())
                .isRejected(true)
                .rejectForPurchaseId(purchase.getId())
                .rejectionId(null)
                .build();
        reject = repository.save(reject);

        // Добавляем в изначальную Purchase отметтку о том, что проведен возврат
        purchase.setRejected(true);
        purchase.setRejectForPurchaseId(null);
        purchase.setRejectionId(reject.getId());
        repository.save(purchase);

        // Возвращаем результат
        RejectionDto rejectDto = PurchaseMapper.toRejectionDto(reject);
        log.info("Reject id={} for purchases id={} successfully created", reject.getId(), purchase.getId());
        return rejectDto;
    }

    @Override
    public List<PurchaseDto> getAllOwnSales(Long sellerId) {
        User buyer = checkAndGetUser(sellerId);

        // Получаем список покупок по продавцу
        List<Purchase> sellsSet = repository.findAllBySeller(buyer);

        // Возвращаем результат
        List<PurchaseDto> sellsDtoSet = sellsSet.stream()
                .map(PurchaseMapper::toPurchaseDto)
                .collect(Collectors.toList());
        log.info("Set of sells of user id={} successfully saved", sellerId);
        return sellsDtoSet;
    }

    private User checkAndGetUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s blocked", userId));
        }
        return user;
    }

    private Company checkAndGetCompany(Long companyId) {

        // Проверяем существует ли компания и актвна ли она
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

        // Проверяем существует ли продукт и актвен ли он
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product id=%s not found", productId)));
        if (Objects.equals(product.getStatus(), ProductStatus.BLOCKED)) {
            throw new ValidationException(String.format("Product id=%s is blocked", productId));
        }
        if (Objects.equals(product.getStatus(), ProductStatus.DELETED)) {
            throw new ValidationException(String.format("Product id=%s is deleted", productId));
        }

        // Проверяем достаточно ли количество продукта
        if (product.getQuantity() < quantity) {
            throw new ValidationException(String.format("Quantity of product id=%s in store: %s, required: %s",
                    productId, product.getQuantity(), quantity));
        }

        // Проверяем совпадает ли цена продукта их базы с ценой в звявке на покупку
        Double priceOfProductWithDiscount;
        if (product.getDiscount() != null) {
            priceOfProductWithDiscount = product.getPrice() - (product.getPrice() * product.getDiscount().getValue());
        } else {
            priceOfProductWithDiscount = product.getPrice();
        }
        if (!Objects.equals(priceOfProductWithDiscount, priceForUnit)) {
            throw new ValidationException(String.format("Price for unit of product id=%s is: %s, required: %s",
                    productId, priceForUnit, priceOfProductWithDiscount));
        }
        return product;
    }

}
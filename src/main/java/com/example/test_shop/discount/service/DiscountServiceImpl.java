package com.example.test_shop.discount.service;

import com.example.test_shop.discount.dto.DiscountAdminUpdateDto;
import com.example.test_shop.discount.dto.DiscountDto;
import com.example.test_shop.discount.dto.NewDiscountDto;
import com.example.test_shop.discount.mapper.DiscountMapper;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.discount.repository.DiscountRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * Class of service for {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;
    private final ProductRepository productRepository;

    @Override
    public DiscountDto add(NewDiscountDto discountDto) {
        Discount newDiscount = DiscountMapper.toDiscount(discountDto);

        // Проверяем, корректно ли задано время скидки сохраняем ее
        checkDiscountTime(newDiscount);
        newDiscount = repository.save(newDiscount);

        // Возвращаем результат
        DiscountDto newDiscountDto = DiscountMapper.toDiscountDto(newDiscount);
        log.info("Discount id={} successfully created", newDiscount.getId());
        return newDiscountDto;
    }

    @Override
    public DiscountDto update(DiscountAdminUpdateDto discountDto, Long discountId) {
        Discount discountFromRepository = checkIsDiscountExist(discountId);

        // Генерируем скидку на основании данных из Dto
        Discount discountForUpdate = DiscountMapper.toDiscount(discountDto);

        // Если параметры скидки из Dto не равны null заменяем ими парметры скидки из базы. Сохраняем обновленную скидку
        discountFromRepository.setDescription(Optional.ofNullable(discountForUpdate.getDescription())
                .orElse(discountFromRepository.getDescription()));
        discountFromRepository.setValue(Optional.ofNullable(discountForUpdate.getValue())
                .orElse(discountFromRepository.getValue()));
        discountFromRepository.setStartDateTime(Optional.ofNullable(discountForUpdate.getStartDateTime())
                .orElse(discountFromRepository.getStartDateTime()));
        discountFromRepository.setFinishDateTime(Optional.ofNullable(discountForUpdate.getFinishDateTime())
                .orElse(discountFromRepository.getFinishDateTime()));
        discountFromRepository = repository.save(discountFromRepository);

        // Возвращаем результат
        DiscountDto updatedDiscountDto = DiscountMapper.toDiscountDto(discountFromRepository);
        log.info("Descount id={} saccssesfully updated", discountId);
        return updatedDiscountDto;
    }

    @Override
    public String delete(Long discountId) {
        Discount discount = repository.findById(discountId)
                .orElseThrow(() -> new NotFoundException(String.format("Discount id=%s not found", discountId)));

        // Ищу все товары на которые действует данная скидка
        Set<Product> productsSet = productRepository.findAllByDiscount(discount);

        // Удаляум скидку из всех найденных товаров и сохраняем их
        if (!productsSet.isEmpty()) {
            for (Product product : productsSet)
                product.setDiscount(null);
        }
        productRepository.saveAll(productsSet);

        // Возвращаем результат
        log.info("Discount id={} successfully deleted", discountId);
        return String.format("Discount id=%s successfully deleted", discountId);
    }

    private Discount checkIsDiscountExist(Long discountId) {
        return repository.findById(discountId).orElseThrow(() -> new NotFoundException(String
                .format("Product didn't add. Discount id=%s not found", discountId)));
    }

    private void checkDiscountTime(Discount discount) {
        if (discount.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Discount start time should in future");
        }
        if (discount.getStartDateTime().isAfter(discount.getFinishDateTime())) {
            throw new ValidationException("Discount start time should be before finish time");
        }
    }

}
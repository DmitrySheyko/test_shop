package com.example.test_shop.discount.service;

import com.example.test_shop.discount.dto.DiscountAdminUpdateDto;
import com.example.test_shop.discount.dto.DiscountDto;
import com.example.test_shop.discount.dto.NewDiscountDto;
import com.example.test_shop.discount.mapper.DiscountMapper;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.discount.repository.DiscountRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

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
        newDiscount = repository.save(newDiscount);
        DiscountDto newDiscountDto = DiscountMapper.toDiscountDto(newDiscount);
        log.info("Discount id={} successfully created", newDiscount.getId());
        return newDiscountDto;
    }

    @Override
    public DiscountDto update(DiscountAdminUpdateDto discountDto, Long discountId) {
        Discount discountFromRepository = repository.findById(discountId)
                .orElseThrow(() -> new NotFoundException(String.format("Discount id=%s not found", discountId)));

        Discount discountForUpdate = DiscountMapper.toDiscount(discountDto);
        discountFromRepository.setDescription(Optional.ofNullable(discountForUpdate.getDescription())
                .orElse(discountFromRepository.getDescription()));
        discountFromRepository.setValue(Optional.ofNullable(discountForUpdate.getValue())
                .orElse(discountFromRepository.getValue()));
        discountFromRepository.setStartDateTime(Optional.ofNullable(discountForUpdate.getStartDateTime())
                .orElse(discountFromRepository.getStartDateTime()));
        discountFromRepository.setFinishDateTime(Optional.ofNullable(discountForUpdate.getFinishDateTime())
                .orElse(discountFromRepository.getFinishDateTime()));

        discountFromRepository = repository.save(discountFromRepository);
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

        // Удаляю скидку из всех найденных товаров
        if (!productsSet.isEmpty()) {
            for (Product product : productsSet)
                product.setDiscount(null);
        }
        productRepository.saveAll(productsSet);

        log.info("Discount id={} successfully deleted", discountId);
        return String.format("Discount id=%s successfully deleted", discountId);
    }

}

package com.example.test_shop.rate.service;

import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.purchase.repository.PurchaseRepository;
import com.example.test_shop.rate.dto.NewRateDto;
import com.example.test_shop.rate.dto.RateDto;
import com.example.test_shop.rate.mapper.RateMapper;
import com.example.test_shop.rate.model.Rate;
import com.example.test_shop.rate.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RateServiceImpl implements RateService {

    private final RateRepository repository;
    private final PurchaseRepository purchaseRepository;


    @Override
    public RateDto add(NewRateDto rateDto, Long userId) {
        // Проверяем покупал ли пользвательданный товар
        Optional<Purchase> optionalPurchase = purchaseRepository.findFirstByBuyerIdAndProductId(userId, rateDto.getProductId());
        if (optionalPurchase.isEmpty()) {
            throw new NotFoundException(String.format("User id=%s couldn't add rate to product id=%s", userId,
                    rateDto.getProductId()));
        }
        // Проверяем ставил ли пользователь оценку данному товару
        if (repository.existsByUserIdAndProductId(userId, rateDto.getProductId())) {
            throw new ValidationException(String.format("User id=%s already add rate to product id=%s", userId,
                    rateDto.getProductId()));
        }
        // Сохраняем оценку
        Rate newRate = RateMapper.toRate(rateDto);
        newRate.setUser(optionalPurchase.get().getBuyer());
        newRate.setProduct(optionalPurchase.get().getProduct());
        newRate.setCreatedOn(LocalDateTime.now());
        newRate = repository.save(newRate);

        RateDto newRateDto = RateMapper.toDto(newRate);
        log.info("Rate id={} successfully add", newRateDto.getId());
        return newRateDto;

    }
}

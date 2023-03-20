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
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Class of service for {@link Rate}
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RateServiceImpl implements RateService {

    private final RateRepository repository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;


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

    @Override
    public String delete(Long userId, Long rateId) {
        // Проверяем существует и не заблокирован ли пользователь
        User user = checkIsUserExistAndActive(userId);

        // Проверяем существует ли оценка с требуемыми userId и rateId
        Optional<Rate> optionalRate = repository.findByIdAndUser(rateId, user);
        if (optionalRate.isEmpty()) {
            throw new NotFoundException(String.format("Rate id=%s from user id=%s not found", rateId, userId));
        }
        repository.delete(optionalRate.get());
        log.info("Rate id={} successfully deleted", rateId);
        return String.format("Rate id=%s successfully deleted", rateId);
    }

    private User checkIsUserExistAndActive(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("Product didn't add. User id=%s not found", userId)));
        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s is blocked", userId));
        }
        return user;
    }
}

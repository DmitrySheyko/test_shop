package com.example.test_shop.rate.service;

import com.example.test_shop.rate.dto.NewRateDto;
import com.example.test_shop.rate.dto.RateDto;
import com.example.test_shop.rate.model.Rate;

/**
 * Interface of service class for {@link Rate}
 *
 * @author DmitrySheyko
 */
public interface RateService {

    RateDto add(NewRateDto rateDto, Long userId);

    String delete(Long userId, Long rateId);

}
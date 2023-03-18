package com.example.test_shop.rate.service;

import com.example.test_shop.rate.dto.NewRateDto;
import com.example.test_shop.rate.dto.RateDto;

public interface RateService {

    RateDto add(NewRateDto rateDto, Long userId);

}

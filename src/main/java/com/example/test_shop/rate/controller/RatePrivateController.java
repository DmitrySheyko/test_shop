package com.example.test_shop.rate.controller;

import com.example.test_shop.rate.dto.NewRateDto;
import com.example.test_shop.rate.dto.RateDto;
import com.example.test_shop.rate.service.RateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/rate")
public class RatePrivateController {

    private final RateService service;

    @PostMapping("/{id}")
    public RateDto add(@Valid NewRateDto rateDto,
                       @Positive @PathVariable(value = "id") Long userId){
        return service.add(rateDto, userId);
    }

}
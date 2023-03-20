package com.example.test_shop.rate.controller;

import com.example.test_shop.rate.dto.NewRateDto;
import com.example.test_shop.rate.dto.RateDto;
import com.example.test_shop.rate.service.RateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/{userId}/rate")
public class RatePrivateController {

    private final RateService service;

    //Добавление новой оценки
    @PostMapping
    public RateDto add(@Valid @RequestBody NewRateDto rateDto,
                       @Positive @PathVariable(value = "userId") Long userId) {
        return service.add(rateDto, userId);
    }

    //Удаление оценки
    @DeleteMapping("/{rateId}")
    public String delete(@Positive @PathVariable(value = "userId") Long userId,
                          @Positive @PathVariable(value = "rateId") Long rateId) {
        return service.delete(userId, rateId);
    }

}

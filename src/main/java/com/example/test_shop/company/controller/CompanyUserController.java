package com.example.test_shop.company.controller;

import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class CompanyUserController {

    private final CompanyService service;

    // Подача пользователем заявки на регистрацию организации
    @PostMapping("/{id}/company")
    public CompanyDto add(@Valid @RequestBody NewCompanyDto companyDto,
                          @Positive @PathVariable(value = "id") Long userId){
        return service.add(companyDto, userId);
    }

}

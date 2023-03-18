package com.example.test_shop.company.controller;

import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/company")
public class CompanyUserController {

    private final CompanyService service;

    // Подача пользователем заявки на регистрацию организации
    @PostMapping
    public CompanyDto add(NewCompanyDto companyDto){
        return service.add(companyDto);
    }

}

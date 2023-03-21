package com.example.test_shop.company.controller;

import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Class of user's controller for {@link Company} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("user/{userId}/company")
public class CompanyUserController {

    private final CompanyService service;

    // Подача пользователем заявки на регистрацию новой организации
    @PostMapping
    public CompanyDto add(@Valid @RequestBody NewCompanyDto companyDto,
                          @Positive @PathVariable(value = "userId") Long userId){
        return service.add(companyDto, userId);
    }

}

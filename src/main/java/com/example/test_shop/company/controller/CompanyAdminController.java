package com.example.test_shop.company.controller;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.service.CompanyService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/company")
public class CompanyAdminController {

    private final CompanyService service;

    @PatchMapping("/{id}")
    public CompanyDto update(CompanyAdminUpdateDto companyDto,
                             @Positive @PathVariable(value = "id") Long companyId) {
        return service.update(companyDto, companyId);
    }

    @DeleteMapping("/{id}")
    public String delete(@Positive @PathVariable(value = "id") Long companyId) {
        return service.delete(companyId);
    }

    @GetMapping
    public Set<CompanyDto> getAllByStatus(@RequestParam (value = "status") Set<String> statusStingSet){
        return service.getAllByStatus(statusStingSet);
    }

}

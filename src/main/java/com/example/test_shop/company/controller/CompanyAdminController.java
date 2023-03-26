package com.example.test_shop.company.controller;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Class of admin controller for {@link Company} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/company")
public class CompanyAdminController {

    private final CompanyService service;

    // Активация администратором новой компании (можно расширить до обновления любых данных о комании)
    @PatchMapping("/{companyId}")
    public CompanyDto update(@Valid @RequestBody CompanyAdminUpdateDto companyDto,
                             @Positive @PathVariable(value = "companyId") Long companyId) {
        return service.update(companyDto, companyId);
    }

    // Удаление компании администратором
    @DeleteMapping("/{companyId}")
    public CompanyDto delete(@Positive @PathVariable(value = "companyId") Long companyId) {
        return service.delete(companyId);
    }

    // Получение администратором списка компаний по их статусу, например компаний ожидающих активации
    @GetMapping
    public List<CompanyDto> searchCompany(@RequestParam(value = "companiesId", required = false) Set<Long> companiesId,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "statuses", required = false) Set<String> statuses,
                                          @RequestParam(value = "ownerId", required = false) Set<Long> ownersId,
                                          @RequestParam(value = "description", required = false) String description) {
        return service.searchCompany(companiesId, name, statuses, ownersId, description);
    }

}
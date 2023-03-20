package com.example.test_shop.company.controller;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/{id}")
    public CompanyDto update(@Valid @RequestBody CompanyAdminUpdateDto companyDto,
                             @Positive @PathVariable(value = "id") Long companyId) {
        return service.update(companyDto, companyId);
    }

    // Удаление администратором компании
    @DeleteMapping("/{id}")
    public String delete(@Positive @PathVariable(value = "id") Long companyId) {
        return service.delete(companyId);
    }

    // Получение администратором списка компаний по их статусу, например компаний ожидающих активации
    @GetMapping
    public Set<CompanyDto> getAllByStatus(@RequestParam (value = "status") Set<String> statusStingSet){
        return service.getAllByStatus(statusStingSet);
    }

}

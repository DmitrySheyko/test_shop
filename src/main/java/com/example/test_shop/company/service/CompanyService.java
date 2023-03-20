package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.model.Company;

import java.util.Set;

/**
 * Interface of service for {@link Company} entity
 *
 * @author DmitrySheyko
 */
public interface CompanyService {

    CompanyDto add(NewCompanyDto companyDto, Long userId);

    String delete(Long companyId);

    CompanyDto update(CompanyAdminUpdateDto companyDto, Long companyId);

    Set<CompanyDto> getAllByStatus(Set<String> statusesSet);
}

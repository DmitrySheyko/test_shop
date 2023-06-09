package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.model.Company;

import java.util.List;
import java.util.Set;

/**
 * Interface of service for {@link Company} entity
 *
 * @author DmitrySheyko
 */
public interface CompanyService {

    CompanyDto add(NewCompanyDto companyDto, Long userId);

    CompanyDto delete(Long companyId);

    CompanyDto update(CompanyAdminUpdateDto companyDto, Long companyId);

    List<CompanyDto> searchCompany(Set<Long> companiesId, String name, Set<String> statusesString,
                                   Set<Long> ownersId, String description);

}

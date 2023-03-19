package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;

import java.util.Set;

public interface CompanyService {

    CompanyDto add(NewCompanyDto companyDto, Long userId);

    String delete(Long companyId);

    CompanyDto update(CompanyAdminUpdateDto companyDto, Long companyId);

    Set<CompanyDto> getAllByStatus(Set<String> statusesSet);
}

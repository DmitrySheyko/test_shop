package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations = classpath:data-test.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CompanyServiceImplTest {

    private final CompanyService service;
    private final CompanyRepository repository;

    @Test
    void add() {
        Long userId = 1L;
        int quantityOfCompaniesBeforeAdd = repository.findAll().size();
        int quantityOfCompaniesAfterAdd = quantityOfCompaniesBeforeAdd + 1;

        // Добавляем четвертую Company
        NewCompanyDto newCompanyDto = NewCompanyDto.builder()
                .name("TestName_4")
                .description("TestDescription_4")
                .logoUrl("TestUrl_4")
                .build();
        CompanyDto companyDto = service.add(newCompanyDto, userId);
        Assertions.assertNotNull(companyDto.getId());
        assertEquals(userId, companyDto.getOwner().getId());
        assertEquals("TestName_4", companyDto.getName());
        assertEquals("TestDescription_4", companyDto.getDescription());
        assertEquals("TestUrl_4", companyDto.getLogoUrl());
        assertEquals("PENDING", companyDto.getStatus());

        assertEquals(quantityOfCompaniesAfterAdd, repository.findAll().size());
    }

    @Test
    void delete() {
        Long companyIdForDelete = 1L;
        CompanyDto companyDto = service.delete(companyIdForDelete);

        // Проверяем, что статус компании изменился на DELETE
        assertEquals("DELETED", companyDto.getStatus());

        // Проверяем, что компания не удалена
        assertTrue(repository.existsById(companyIdForDelete));
    }

    @Test
    void update() {
        Long userId = 1L;

        // Добавляем компанию для обновления
        NewCompanyDto newCompanyDto = NewCompanyDto.builder()
                .name("TestName_4")
                .description("TestDescription_4")
                .logoUrl("TestUrl_4")
                .build();
        CompanyDto companyDto = service.add(newCompanyDto, userId);

        // Обновляем статус компании на ACTIVE
        CompanyAdminUpdateDto updateDto = CompanyAdminUpdateDto.builder()
                .status("ACTIVE")
                .build();
        CompanyDto updatedCompanyDto = service.update(updateDto, companyDto.getId());
        assertEquals("ACTIVE", updatedCompanyDto.getStatus());

        // Обновляем статус компании на BLOCKED
        updateDto = CompanyAdminUpdateDto.builder()
                .status("BLOCKED")
                .build();
        updatedCompanyDto = service.update(updateDto, companyDto.getId());
        assertEquals("BLOCKED", updatedCompanyDto.getStatus());
    }

    @Test
    void searchCompany() {
        Set<Long> companiesId = null;
        String name = null;
        Set<String> statuses = null;
        Set<Long> ownersId = null;
        String description = null;

        name = "TestCompanyName_1";
        List<CompanyDto> companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        assertEquals(1, companyDtoList.size());
        assertEquals("TestCompanyName_1", companyDtoList.get(0).getName());

        name = null;
        statuses = Set.of("PENDING");
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        assertEquals(0, companyDtoList.size());

        statuses = Set.of("ACTIVE");
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        assertEquals(3, companyDtoList.size());


        statuses = null;
        ownersId = Set.of(1L);
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        assertEquals(2, companyDtoList.size());

        ownersId = null;
        description = "_1";
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        assertEquals(1, companyDtoList.size());
        assertEquals("TestCompanyDescription_1", companyDtoList.get(0).getDescription());
    }

}
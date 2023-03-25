package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.repository.CompanyRepository;
import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CompanyServiceImplTest {

    private final CompanyService service;
    private final CompanyRepository repository;
    private final UserService userService;
    UserDto user_1Dto;
    CompanyDto company_1Dto;
    CompanyDto company_2Dto;
    UserDto user_2Dto;
    CompanyDto company_3Dto;
    CompanyDto company_4Dto;

    @BeforeEach
    // Создаю двух User и четрые Company
    void beforeEach(){
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_1")
                .password("TestPassword_1")
                .email("Test_1@email.ru")
                .role("ROLE_USER")
                .build();
        user_1Dto = userService.add(newUserDto);

        newUserDto = NewUserDto.builder()
                .username("TestName_2")
                .password("TestPassword_2")
                .email("Test_2@email.ru")
                .role("ROLE_USER")
                .build();
        user_2Dto = userService.add(newUserDto);

        NewCompanyDto newCompanyDto = NewCompanyDto.builder()
                .name("TestName_1")
                .description("TestDescription_1")
                .logoUrl("TestUrl_1")
                .build();
        company_1Dto = service.add(newCompanyDto, user_1Dto.getId());

        newCompanyDto = NewCompanyDto.builder()
                .name("TestName_2")
                .description("TestDescription_2")
                .logoUrl("TestUrl_2")
                .build();
        company_2Dto = service.add(newCompanyDto, user_1Dto.getId());

         newCompanyDto = NewCompanyDto.builder()
                .name("TestName_3")
                .description("TestDescription_3")
                .logoUrl("TestUrl_3")
                .build();
        company_3Dto = service.add(newCompanyDto, user_1Dto.getId());

        newCompanyDto = NewCompanyDto.builder()
                .name("TestName_4")
                .description("TestDescription_4")
                .logoUrl("TestUrl_4")
                .build();
       company_4Dto = service.add(newCompanyDto, user_1Dto.getId());

    }

    @Test
    void add() {
        int companiesBeforeTest = 4;
        int companiesAfterTest = 5;

        Assertions.assertEquals(companiesBeforeTest, repository.findAll().size());

        // Добавляю пятую Company
        NewCompanyDto newCompanyDto = NewCompanyDto.builder()
                .name("TestName_5")
                .description("TestDescription_5")
                .logoUrl("TestUrl_5")
                .build();
        CompanyDto companyDto = service.add(newCompanyDto, user_1Dto.getId());
        Assertions.assertNotNull(companyDto.getId());
        Assertions.assertEquals(user_1Dto.getId(), companyDto.getOwner().getId());
        Assertions.assertEquals("TestName_5", companyDto.getName());
        Assertions.assertEquals("TestDescription_5", companyDto.getDescription());
        Assertions.assertEquals("TestUrl_5", companyDto.getLogoUrl());
        Assertions.assertEquals("PENDING", companyDto.getStatus());

        Assertions.assertEquals(companiesAfterTest, repository.findAll().size());
    }

    @Test
    void delete() {
        int quantityOfCompaniesBeforeDelete = repository.findAll().size();

        service.delete(company_1Dto.getId());
        int quantityOfCompaniesAfterDelete = repository.findAll().size();
        Assertions.assertEquals(quantityOfCompaniesBeforeDelete - 1, quantityOfCompaniesAfterDelete);

        service.delete(company_2Dto.getId());
        quantityOfCompaniesAfterDelete = repository.findAll().size();
        Assertions.assertEquals(quantityOfCompaniesBeforeDelete - 2, quantityOfCompaniesAfterDelete);
    }

    @Test
    void update() {
        // Обновляю статус компании на ACTIVE
        CompanyAdminUpdateDto updateDto = CompanyAdminUpdateDto.builder()
                .status("ACTIVE")
                .build();
        CompanyDto updatedCompanyDto = service.update(updateDto, company_1Dto.getId());
        Assertions.assertEquals("ACTIVE", updatedCompanyDto.getStatus());

        // Обновляю статус компании на BLOCKED
        updateDto = CompanyAdminUpdateDto.builder()
                .status("BLOCKED")
                .build();
        updatedCompanyDto = service.update(updateDto, company_1Dto.getId());
        Assertions.assertEquals("BLOCKED", updatedCompanyDto.getStatus());
    }

    @Test
    void searchCompany() {
        Set<Long> companiesId = null;
        String name = null;
        Set<String> statuses = null;
        Set<Long> ownersId = null;
        String description = null;

        name= "TestName_1";
        List<CompanyDto> companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        Assertions.assertEquals(1, companyDtoList.size());
        Assertions.assertEquals("TestName_1", companyDtoList.get(0).getName());

        name= null;
        statuses = Set.of("PENDING");
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        Assertions.assertEquals(4, companyDtoList.size());

        statuses = null;
        ownersId = Set.of(user_1Dto.getId(), user_2Dto.getId());
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        Assertions.assertEquals(4, companyDtoList.size());

        ownersId = null;
        description = "_1";
        companyDtoList = service.searchCompany(companiesId, name, statuses, ownersId, description);
        Assertions.assertEquals(1, companyDtoList.size());
        Assertions.assertEquals("TestDescription_1", companyDtoList.get(0).getDescription());
    }

}

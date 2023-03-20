package com.example.test_shop.company.mapper;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.dto.CompanyShortDto;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Class of mapper {@link Company} entity
 *
 * @author DmitrySheyko
 */
@Component
public class CompanyMapper {

    public static Company toCompany(NewCompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        } else {
            return Company.builder()
                    .name(companyDto.getName())
                    .description(companyDto.getDescription())
                    .logoUrl(companyDto.getLogoUrl())
                    .build();
        }
    }

    public static Company toCompany(CompanyAdminUpdateDto companyDto) {
        if (companyDto == null) {
            return null;
        } else {
            return Company.builder()
                    .status(CompanyStatus.valueOf(companyDto.getStatus()))
                    .build();
        }
    }

    public static CompanyDto toDto(Company company) {
        if (company == null) {
            return null;
        } else {
            return CompanyDto.builder()
                    .id(company.getId())
                    .name(company.getName())
                    .owner(UserMapper.toUserShortDto(company.getOwner()))
                    .description(company.getDescription())
                    .logoUrl(company.getLogoUrl())
                    .status(company.getStatus().name())
                    .build();
        }
    }

    public static CompanyShortDto toShortDto(Company company) {
        if (company == null) {
            return null;
        } else {
            return CompanyShortDto.builder()
                    .id(company.getId())
                    .name(company.getName())
                    .build();
        }
    }

}

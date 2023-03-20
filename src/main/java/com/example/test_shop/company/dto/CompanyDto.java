package com.example.test_shop.company.dto;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for getting full information about {@link Company} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class CompanyDto {

    private Long id;
    private String name;
    private UserShortDto owner;
    private String description;
    private String logoUrl;
    private String status;

}

package com.example.test_shop.company.dto;

import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

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

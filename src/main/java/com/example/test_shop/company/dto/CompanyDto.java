package com.example.test_shop.company.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDto {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private String status;

}

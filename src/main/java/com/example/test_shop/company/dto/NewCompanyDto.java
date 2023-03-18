package com.example.test_shop.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class NewCompanyDto {

    @NotBlank
    private String name;
    private String description;
    @URL
    private String logoUrl;
}

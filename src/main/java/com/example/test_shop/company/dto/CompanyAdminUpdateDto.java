package com.example.test_shop.company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAdminUpdateDto {

    @NotNull
    @NotBlank
    private String status;

}

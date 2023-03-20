package com.example.test_shop.company.dto;

import com.example.test_shop.company.model.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Class of dto for update {@link Company} entity by admin
 *
 * @author DmitrySheyko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAdminUpdateDto {

    @NotNull
    @NotBlank
    private String status;

}

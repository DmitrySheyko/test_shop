package com.example.test_shop.company.dto;

import com.example.test_shop.company.model.Company;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * Class of dto for creating new {@link Company} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewCompanyDto {

    @NotBlank
    private String name;
    private String description;

    @URL
    private String logoUrl;

}

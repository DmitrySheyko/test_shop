package com.example.test_shop.company.dto;

import com.example.test_shop.company.model.Company;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for getting short information about {@link Company} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class CompanyShortDto {

    private Long id;
    private String name;

}

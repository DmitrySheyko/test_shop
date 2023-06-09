package com.example.test_shop.company.repository;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;

import java.util.List;
import java.util.Set;

/**
 * Interface of custom repository for {@link Company}
 *
 * @author Dmitry Sheyko
 */
public interface CompanyRepositoryCustom {

    List<Company> searchCompany(Set<Long> companiesId, String name, Set<CompanyStatus> statuses,
                                Set<Long> ownersId, String description);

}
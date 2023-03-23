package com.example.test_shop.company.repository;

import com.example.test_shop.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of repository for {@link Company} entity
 *
 * @author DmitrySheyko
 */
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {


}

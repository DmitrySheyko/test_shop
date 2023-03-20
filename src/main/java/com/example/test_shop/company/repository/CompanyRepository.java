package com.example.test_shop.company.repository;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

/**
 * Interface of repository for {@link Company} entity
 *
 * @author DmitrySheyko
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Set<Company> findAllByStatusIn(Set<CompanyStatus> status);
}

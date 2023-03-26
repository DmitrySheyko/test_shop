package com.example.test_shop.product.repository;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.model.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Interface of repository for {@link Product} entity
 *
 * @author DmitrySheyko
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findAllByDiscount(Discount discount);

    List<Product> findAllByCompany(Company company);

    Page<Product> findAllByStatusAndCompanyStatus(ProductStatus active, CompanyStatus active1, Pageable pageable);

}

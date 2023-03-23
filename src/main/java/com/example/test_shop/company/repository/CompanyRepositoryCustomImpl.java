package com.example.test_shop.company.repository;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class of custom repository for {@link Company}
 *
 * @author Dmitry Sheyko
 */
public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Company> searchCompany(Set<Integer> companiesId, Set<String> names, Set<CompanyStatus> statuses,
                                       Set<String> ownersId, Set<String> description) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> query = cb.createQuery(Company.class);
        Root<Company> company = query.from(Company.class);

        List<Predicate> predicates = new ArrayList<>();

        if (companiesId != null && !companiesId.isEmpty()) {
            predicates.add(cb.and(company.get("id").in(companiesId)));
        }
        if (names != null && !names.isEmpty()) {
            predicates.add(cb.and(company.get("name").in(names)));
        }
        if (statuses != null && !statuses.isEmpty()) {
            predicates.add(cb.and(company.get("status").in(statuses)));
        }
        if (ownersId != null && !ownersId.isEmpty()) {
            predicates.add(cb.and(company.get("email").in(ownersId)));
        }
        if (description != null && !description.isEmpty()) {
            predicates.add(cb.and(company.get("role").in(description)));
        }

        query.select(company).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(query).getResultList();
    }

}


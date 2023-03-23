package com.example.test_shop.user.repository;

import com.example.test_shop.user.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class of custom repository for {@link User} entity
 *
 * @author DmitrySheyko
 */
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> searchUser(Set<String> usersId, Set<String> usernames, Set<String> emails, Set<String> roles,
                                 Double balanceEqual, Double balanceMoreOrEqual, Double balanceLessOrEqual) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (usersId != null && !usersId.isEmpty()) {
            predicates.add(cb.and(user.get("id").in(usersId)));
        }
        if (usernames != null && !usernames.isEmpty()) {
            predicates.add(cb.and(user.get("username").in(usernames)));
        }
        if (emails != null && !emails.isEmpty()) {
            predicates.add(cb.and(user.get("email").in(emails)));
        }
        if (roles != null && !roles.isEmpty()) {
            predicates.add(cb.and(user.get("role").in(roles)));
        }
        if (balanceEqual != null) {
            predicates.add(cb.equal(user.get("balance"), balanceEqual));
        }
        if (balanceMoreOrEqual != null) {
            predicates.add(cb.ge(user.get("balance"), balanceMoreOrEqual));
        }
        if (balanceLessOrEqual != null) {
            predicates.add(cb.le(user.get("balance"), balanceLessOrEqual));
        }

        query.select(user).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(query).getResultList();
    }

}
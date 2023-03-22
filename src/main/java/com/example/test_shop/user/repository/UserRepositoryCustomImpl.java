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
    public List<User> searchUser(Set<String> usersId, Set<String> usernames, Set<String> emails) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (usersId != null) {
            predicates.add(cb.and(user.get("id").in(usersId)));
        }
        if (usernames != null) {
            predicates.add(cb.and(user.get("username").in(usernames)));
        }
        if (emails != null) {
            predicates.add(cb.and(user.get("email").in(emails)));
        }

        query.select(user).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(query).getResultList();
    }

}
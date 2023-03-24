package com.example.test_shop.user.repository;

import com.example.test_shop.user.model.User;

import java.util.List;
import java.util.Set;

/**
 * Interface of custom repository for {@link User} entity
 *
 * @author DmitrySheyko
 */
public interface UserRepositoryCustom {

    List<User> searchUser(Set<Long> usersId, Set<String> usernames, Set<String> emails, Set<String> roles,
                          Double balanceEqual, Double balanceMoreOrEqual, Double balanceLessOrEqual);

}

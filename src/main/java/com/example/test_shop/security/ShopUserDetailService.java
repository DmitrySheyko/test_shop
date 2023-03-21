package com.example.test_shop.security;

import com.example.test_shop.user.model.User;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of UserDetailsService
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShopUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.info("User {} not found", username);
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        } else {
            log.info("User {} successfully found", username);
            user = optionalUser.get();
        }
        return new ShopUserDetails(user);
    }

}
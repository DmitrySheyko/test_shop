package com.example.test_shop.security;

import com.example.test_shop.user.model.User;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class ShopUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Программа ищет:" + username);
        User user;
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        } else {
            user = optionalUser.get();
        }
        System.out.println("Нашла:" + user.getUsername());
        System.out.println("Нашла:" + user.getPassword());
        return new ShopUserDetails(user);
    }

}
package com.example.test_shop.user.service;

import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.mapper.UserMapper;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class of service for {@link User} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserAdminDto add(NewUserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setStatus(UserStatus.ACTIVE);
        user.setBalance(0.0);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = repository.save(user);
        UserAdminDto createdUserAdminDto = UserMapper.toUserDto(user);
        log.info("User id={} successfully created", user.getId());
        return createdUserAdminDto;
    }

    @Override
    public UserAdminDto update(UserAdminUpdateDto userDtoForUpdate, Long userId) {
        User userFromRepository = repository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("User didn't update. User id=%s not found", userId)));

        User userFoUpdate = UserMapper.toUser(userDtoForUpdate);
        userFromRepository.setUsername(Optional.ofNullable(userFoUpdate.getUsername()).orElse(userFromRepository.getUsername()));
        userFromRepository.setEmail(Optional.ofNullable(userFoUpdate.getEmail()).orElse(userFromRepository.getEmail()));
        userFromRepository.setBalance(Optional.ofNullable(userFoUpdate.getBalance()).orElse(userFromRepository.getBalance()));
        userFromRepository.setStatus(Optional.ofNullable(userFoUpdate.getStatus()).orElse(userFromRepository.getStatus()));
        if (userFoUpdate.getPassword() != null) {
            userFromRepository.setPassword(bCryptPasswordEncoder.encode(userFoUpdate.getPassword()));
        }

        userFromRepository = repository.save(userFromRepository);

        UserAdminDto updatedUserAdminDto = UserMapper.toUserDto(userFromRepository);
        log.info("User id={} successfully updated", userId);
        return updatedUserAdminDto;
    }

    @Override
    public String delete(Long userId) {
        User userForDelete = repository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("User didn't delete. User id=%s not found", userId)));
        repository.delete(userForDelete);
        log.info("User id={} successfully deleted", userId);
        return String.format("User id=%s successfully deleted", userId);
    }

}

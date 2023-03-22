package com.example.test_shop.user.service;

import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public UserDto add(NewUserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setStatus(UserStatus.ACTIVE);
        user.setBalance(0.0);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = repository.save(user);
        UserDto createdUserDto = UserMapper.toUserDto(user);
        log.info("User id={} successfully created", user.getId());
        return createdUserDto;
    }

    @Override
    public UserDto update(UserAdminUpdateDto userDtoForUpdate, Long userId) {

        /* не используем метод checkAndGetUser т.е. он отсекает заблокированных  пользователей,
        а update используется в том числе для разблокировки
         */
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

        UserDto updatedUserDto = UserMapper.toUserDto(userFromRepository);
        log.info("User id={} successfully updated", userId);
        return updatedUserDto;
    }

    @Override
    public String delete(Long userId) {
        User userForDelete = checkAndGetUser(userId);
        repository.delete(userForDelete);
        log.info("User id={} successfully deleted", userId);
        return String.format("User id=%s successfully deleted", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getOwnAccount(Long userId) {
        User user = checkAndGetUser(userId);
        UserDto userDto = UserMapper.toUserDto(user);
        log.info("User id={} successfully received", userId);
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getAnotherUser(Long userId, Long anotherUserId) {
        checkAndGetUser(userId);
        User anotherUser = checkAndGetUser(anotherUserId);
        UserDto anotherUserDto = UserMapper.toUserDto(anotherUser);
        log.info("User id={} successfully received", anotherUserId);
        return anotherUserDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> searchUsers(Set<String> usersId, Set<String> usernames, Set<String> emails) {
        List<User> usersSet = repository.searchUser(usersId, usernames, emails);
        List<UserDto> usersDtoSet = usersSet.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        log.info("List of users successfully received");
        return usersDtoSet;
    }

    private User checkAndGetUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User id=%s not found", userId)));
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s blocked", userId));
        }
        return user;
    }

}

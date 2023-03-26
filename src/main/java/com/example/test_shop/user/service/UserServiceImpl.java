package com.example.test_shop.user.service;

import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserShortDto;
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

        // Генерируем нового пользователя на основе данных из DTO
        User user = UserMapper.toUser(userDto);

        // Добавляем исходные данные
        user.setStatus(UserStatus.ACTIVE);
        user.setBalance(0.0);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = repository.save(user);

        // Возвращаем результат
        UserDto createdUserDto = UserMapper.toUserDto(user);
        log.info("User id={} successfully created", user.getId());
        return createdUserDto;
    }

    @Override
    public UserDto update(UserAdminUpdateDto userDtoForUpdate, Long userId) {

        /* Получаем пользователя из базы. Не используем метод checkAndGetUser т.е. он отсекает заблокированных
        пользователей, а update используется в том числе для разблокировки
         */
        User userFromRepository = repository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("User didn't update. User id=%s not found", userId)));

        // Генерируем пользователя на основе данных из Dto
        User userFoUpdate = UserMapper.toUser(userDtoForUpdate);

        /* Если параметры пользователя из Dto не равны null добавляем заменяем ими парметры пользователя из базы.
         * Сохраняем обновленного пользователя
         */
        userFromRepository.setUsername(Optional.ofNullable(userFoUpdate.getUsername()).orElse(userFromRepository.getUsername()));
        userFromRepository.setEmail(Optional.ofNullable(userFoUpdate.getEmail()).orElse(userFromRepository.getEmail()));
        userFromRepository.setBalance(Optional.ofNullable(userFoUpdate.getBalance()).orElse(userFromRepository.getBalance()));
        userFromRepository.setStatus(Optional.ofNullable(userFoUpdate.getStatus()).orElse(userFromRepository.getStatus()));
        if (userFoUpdate.getPassword() != null) {
            userFromRepository.setPassword(bCryptPasswordEncoder.encode(userFoUpdate.getPassword()));
        }
        userFromRepository = repository.save(userFromRepository);

        // Возвращаем результат
        UserDto updatedUserDto = UserMapper.toUserDto(userFromRepository);
        log.info("User id={} successfully updated", userId);
        return updatedUserDto;
    }

    @Override
    public String delete(Long userId) {
        User userForDelete = checkAndGetUser(userId);

        // Удаляем пользователя из базы. Его компании, продукты, покупки удалены не будут.
        repository.delete(userForDelete);

        // Возвращаем результат
        log.info("User id={} successfully deleted", userId);
        return String.format("User id=%s successfully deleted", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getOwnAccount(Long userId) {
        User user = checkAndGetUser(userId);

        // Возвращаем результат
        UserDto userDto = UserMapper.toUserDto(user);
        log.info("User id={} successfully received", userId);
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserShortDto getAnotherUser(Long userId, Long anotherUserId) {
        checkAndGetUser(userId);
        User anotherUser = checkAndGetUser(anotherUserId);

        // Возвращаем результат
        UserShortDto anotherUserShortDto = UserMapper.toUserShortDto(anotherUser);
        log.info("User id={} successfully received", anotherUserId);
        return anotherUserShortDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> searchUsers(Set<Long> usersId, Set<String> usernames, Set<String> emails, Set<String> roles,
                                     Double balanceEqual, Double balanceMoreOrEqual, Double balanceLessOrEqual) {
        List<User> usersSet = repository.searchUser(usersId, usernames, emails, roles, balanceEqual,
                balanceMoreOrEqual, balanceLessOrEqual);

        // Возвращаем результат
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
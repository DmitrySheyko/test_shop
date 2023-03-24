package com.example.test_shop.user.service;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserShortDto;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    private final UserService service;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Test
    void add() {
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);
        /* При тестовой иницаилазиции в базу вносится один админитратор и четыре пользователя.
         * Создаваемый в тесте пользователь должен иметь Id = 6
         */
        Assertions.assertEquals("TestName_6", userDto.getUsername());
        Assertions.assertTrue(passwordEncoder.matches("TestPassword_6", userDto.getPassword()));
        Assertions.assertEquals("Test_6@email.ru", userDto.getEmail());
        Assertions.assertEquals("ROLE_USER", userDto.getRole());
        Assertions.assertEquals("ACTIVE", userDto.getStatus());
    }

    @Test
    void shouldThroeExceptionWhenAddUserWithEqualUsername() {
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .role("ROLE_USER")
                .build();
        service.add(newUserDto);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.add(newUserDto));
    }

    @Test
    void update() {
        Long userForUpdateId = 1L;
        UserAdminUpdateDto updateDto = UserAdminUpdateDto.builder()
                .username("TestName_1(updated)")
                .password("TestPassword_1(updated)")
                .email("Test_1(updated)@email.ru")
                .balance(15000.0)
                .status("BLOCKED")
                .build();
        UserDto updatedUserDto = service.update(updateDto, userForUpdateId);
        Assertions.assertEquals(1, updatedUserDto.getId());
        Assertions.assertEquals("TestName_1(updated)", updatedUserDto.getUsername());
        Assertions.assertTrue(passwordEncoder.matches("TestPassword_1(updated)", updatedUserDto.getPassword()));
        Assertions.assertEquals("Test_1(updated)@email.ru", updatedUserDto.getEmail());
        Assertions.assertEquals("BLOCKED", updatedUserDto.getStatus());
    }

    @Test
    void delete() {
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);
        Long userIdForDelete = userDto.getId();

        int beforeDeleteUsersQuantity = repository.findAll().size();
        service.delete(userIdForDelete);
        int afterDeleteUsersQuantity = repository.findAll().size();
        Assertions.assertEquals(beforeDeleteUsersQuantity - 1, afterDeleteUsersQuantity);
    }

    @Test
    void getOwnAccount() {
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);
        UserDto userDtoFromDB = service.getOwnAccount(userDto.getId());
        Assertions.assertEquals(userDto.getId(), userDtoFromDB.getId());
        Assertions.assertEquals(userDto.getUsername(), userDtoFromDB.getUsername());
        Assertions.assertEquals(userDto.getPassword(), userDtoFromDB.getPassword());
        Assertions.assertEquals(userDto.getEmail(), userDtoFromDB.getEmail());
        Assertions.assertEquals(userDto.getRole(), userDtoFromDB.getRole());
        Assertions.assertEquals(userDto.getStatus(), userDtoFromDB.getStatus());
    }

    @Test
    void getAnotherUser() {
        NewUserDto requesterNewUserDto = NewUserDto.builder()
                .username("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto requesterUserDto = service.add(requesterNewUserDto);
        Long requesterId = requesterUserDto.getId();

        NewUserDto anotherNewUserDto = NewUserDto.builder()
                .username("TestName_7")
                .password("TestPassword_7")
                .email("Test_7@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto anotherUserDto = service.add(anotherNewUserDto);
        Long anotherUserId = anotherUserDto.getId();

        UserShortDto userShortDto = service.getAnotherUser(requesterId, anotherUserId);
        Assertions.assertEquals(anotherUserId, userShortDto.getId());
        Assertions.assertEquals("TestName_7", userShortDto.getUsername());
        Assertions.assertEquals("Test_7@email.ru", userShortDto.getEmail());
    }

    @Test
    void searchUsers() {
        Set<Long> usersId = null;
        Set<String> usernames = null;
        Set<String> emails = null;
        Set<String> roles = null;
        Double balanceEqual = null;
        Double balanceMoreOrEqual = null;
        Double balanceLessOrEqual = null;

        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_6")
                .password("TestPassword_6")
                .email("Test_6@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);
        Long userIdForSearch = userDto.getId();
        usersId = Set.of(userIdForSearch);
        List<UserDto> userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        usersId = null;
        usernames = Set.of("TestName_2", "TestName_3");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());

        usernames = null;
        emails = Set.of("Test_1@email.ru", "Test_2@email.ru", "Test_3@email.ru", "Test_4@email.ru");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(4, userDtoList.size());

        emails = null;
        roles = Set.of("ROLE_ADMIN");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        roles = null;
        balanceEqual = 40000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        balanceEqual = null;
        balanceMoreOrEqual = 30000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());

        balanceMoreOrEqual = null;
        balanceLessOrEqual = 10000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(3, userDtoList.size());

        balanceLessOrEqual = null;
        balanceEqual = 10000.0;
        roles = Set.of("ROLE_ADMIN");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());
    }

}
package com.example.test_shop.user.service;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.dto.UserShortDto;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    UserDto user_1Dto;
    UserDto user_2Dto;

    @BeforeEach
    void beforeEach() {
        // Создаю двух User и четрые Company
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_1")
                .password("TestPassword_1")
                .email("Test_1@email.ru")
                .role("ROLE_USER")
                .build();
        user_1Dto = service.add(newUserDto);

        newUserDto = NewUserDto.builder()
                .username("TestName_2")
                .password("TestPassword_2")
                .email("Test_2@email.ru")
                .role("ROLE_USER")
                .build();
        user_2Dto = service.add(newUserDto);
    }

    @Test
    void add() {
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_3")
                .password("TestPassword_3")
                .email("Test_3@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);
        Assertions.assertEquals("TestName_3", userDto.getUsername());
        Assertions.assertTrue(passwordEncoder.matches("TestPassword_3", userDto.getPassword()));
        Assertions.assertEquals("Test_3@email.ru", userDto.getEmail());
        Assertions.assertEquals("ROLE_USER", userDto.getRole());
        Assertions.assertEquals("ACTIVE", userDto.getStatus());
    }

    @Test
    void shouldThroeExceptionWhenAddUserWithEqualUsername() {
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_1")
                .password("TestPassword_1")
                .email("Test_1@email.ru")
                .role("ROLE_USER")
                .build();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.add(newUserDto));
    }

    @Test
    void update() {
        UserAdminUpdateDto updateDto = UserAdminUpdateDto.builder()
                .username("TestName_1(updated)")
                .password("TestPassword_1(updated)")
                .email("Test_1(updated)@email.ru")
                .balance(15000.0)
                .status("BLOCKED")
                .build();
        UserDto updatedUserDto = service.update(updateDto, user_1Dto.getId());
        Assertions.assertEquals("TestName_1(updated)", updatedUserDto.getUsername());
        Assertions.assertTrue(passwordEncoder.matches("TestPassword_1(updated)", updatedUserDto.getPassword()));
        Assertions.assertEquals("Test_1(updated)@email.ru", updatedUserDto.getEmail());
        Assertions.assertEquals("BLOCKED", updatedUserDto.getStatus());
    }

    @Test
    void delete() {
        int beforeDeleteUsersQuantity = repository.findAll().size();
        service.delete(user_1Dto.getId());
        int afterDeleteUsersQuantity = repository.findAll().size();
        Assertions.assertEquals(beforeDeleteUsersQuantity - 1, afterDeleteUsersQuantity);
    }

    @Test
    void getOwnAccount() {
        UserDto userDtoFromDB = service.getOwnAccount(user_1Dto.getId());
        Assertions.assertEquals(user_1Dto.getId(), userDtoFromDB.getId());
        Assertions.assertEquals(user_1Dto.getUsername(), userDtoFromDB.getUsername());
        Assertions.assertEquals(user_1Dto.getPassword(), userDtoFromDB.getPassword());
        Assertions.assertEquals(user_1Dto.getEmail(), userDtoFromDB.getEmail());
        Assertions.assertEquals(user_1Dto.getRole(), userDtoFromDB.getRole());
        Assertions.assertEquals(user_1Dto.getStatus(), userDtoFromDB.getStatus());
    }

    @Test
    void getAnotherUser() {
        UserShortDto anotherUserShortDto = service.getAnotherUser(user_1Dto.getId(), user_2Dto.getId());
        Assertions.assertEquals(anotherUserShortDto.getId(), user_2Dto.getId());
        Assertions.assertEquals(anotherUserShortDto.getUsername(), user_2Dto.getUsername());
        Assertions.assertEquals(anotherUserShortDto.getEmail(), user_2Dto.getEmail());
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


        usersId = Set.of(user_1Dto.getId());
        List<UserDto> userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        usersId = Set.of(user_1Dto.getId(), user_2Dto.getId());
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());

        usersId = null;
        usernames = Set.of("TestName_1");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        usersId = null;
        usernames = Set.of("TestName_1", "TestName_2");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());

        usernames = null;
        emails = Set.of("Test_1@email.ru");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        emails = Set.of("Test_1@email.ru", "Test_2@email.ru");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());

        emails = null;
        roles = Set.of("ROLE_ADMIN");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        roles = Set.of("ROLE_ADMIN", "ROLE_USER");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(3, userDtoList.size());

        roles = null;
        balanceEqual = 0.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(3, userDtoList.size());


        UserAdminUpdateDto updateDto = UserAdminUpdateDto.builder()
                .balance(10000.0)
                .build();
        service.update(updateDto, user_1Dto.getId());

        updateDto = UserAdminUpdateDto.builder()
                .balance(20000.0)
                .build();
        service.update(updateDto, user_1Dto.getId());

        balanceEqual = null;
        balanceMoreOrEqual = 20000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(1, userDtoList.size());

        balanceMoreOrEqual = null;
        balanceLessOrEqual = 10000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        Assertions.assertEquals(2, userDtoList.size());
    }

}
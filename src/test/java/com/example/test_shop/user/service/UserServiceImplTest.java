package com.example.test_shop.user.service;

import com.example.test_shop.company.repository.CompanyRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations=classpath:data-test.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceImplTest {

    private final UserService service;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    @Test
    void add() {
        int quantityOfUsersBeforeAdd = repository.findAll().size();
        int quantityOfUsersAfterAdd = quantityOfUsersBeforeAdd + 1;

        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_5")
                .password("TestPassword_5")
                .email("Test_5@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);
        assertEquals("TestName_5", userDto.getUsername());
        assertTrue(passwordEncoder.matches("TestPassword_5", userDto.getPassword()));
        assertEquals("Test_5@email.ru", userDto.getEmail());
        assertEquals("ROLE_USER", userDto.getRole());
        assertEquals("ACTIVE", userDto.getStatus());

        assertEquals(quantityOfUsersAfterAdd, repository.findAll().size());
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
        NewUserDto newUserDto = NewUserDto.builder()
                .username("TestName_5")
                .password("TestPassword_5")
                .email("Test_5@email.ru")
                .role("ROLE_USER")
                .build();
        UserDto userDto = service.add(newUserDto);

        UserAdminUpdateDto updateDto = UserAdminUpdateDto.builder()
                .username("TestName_1(updated)")
                .password("TestPassword_1(updated)")
                .email("Test_1(updated)@email.ru")
                .balance(15000.0)
                .status("BLOCKED")
                .build();
        UserDto updatedUserDto = service.update(updateDto, userDto.getId());
        assertEquals("TestName_1(updated)", updatedUserDto.getUsername());
        assertTrue(passwordEncoder.matches("TestPassword_1(updated)", updatedUserDto.getPassword()));
        assertEquals("Test_1(updated)@email.ru", updatedUserDto.getEmail());
        assertEquals("BLOCKED", updatedUserDto.getStatus());
    }

    @Test
    void delete() {
        Long userIdForDelete = 1L;
        Long companyIdOfUser = 1L;
        int quantityOfUsersBeforeDelete = repository.findAll().size();
        int quantityOfCompaniesBeforeDelete = companyRepository.findAll().size();

        service.delete(userIdForDelete);

        int quantityOfUsersAfterDelete = repository.findAll().size();
        int quantityOfCompaniesAfterDelete = companyRepository.findAll().size();
        assertEquals(quantityOfUsersBeforeDelete - 1, quantityOfUsersAfterDelete);
        assertEquals(quantityOfCompaniesBeforeDelete, quantityOfCompaniesAfterDelete);
        assertTrue(companyRepository.existsById(companyIdOfUser));
    }

    @Test
    void getOwnAccount() {
        Long userId = 1L;
        UserDto userDtoFromDB = service.getOwnAccount(userId);
        assertEquals(userId, userDtoFromDB.getId());
        assertEquals("TestName_1", userDtoFromDB.getUsername());
        assertEquals("Test_password_1", userDtoFromDB.getPassword());
        assertEquals("Test_1@email.ru", userDtoFromDB.getEmail());
        assertEquals("ROLE_USER", userDtoFromDB.getRole());
        assertEquals("ACTIVE", userDtoFromDB.getStatus());
    }

    @Test
    void getAnotherUser() {
        Long userId = 1L;
        Long anotherUserId = 2L;
        UserShortDto anotherUserShortDto = service.getAnotherUser(userId, anotherUserId);
        assertEquals(anotherUserShortDto.getId(), anotherUserShortDto.getId());
        assertEquals("TestName_2", anotherUserShortDto.getUsername());
        assertEquals("Test_2@email.ru", anotherUserShortDto.getEmail());
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


        usersId = Set.of(1L);
        List<UserDto> userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(1, userDtoList.size());

        usersId = Set.of(1L, 2L);
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(2, userDtoList.size());

        usersId = null;
        usernames = Set.of("TestName_1");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(1, userDtoList.size());

        usernames = Set.of("TestName_1", "TestName_2");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(2, userDtoList.size());

        usernames = null;
        emails = Set.of("Test_1@email.ru");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(1, userDtoList.size());

        emails = Set.of("Test_1@email.ru", "Test_2@email.ru");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(2, userDtoList.size());

        emails = null;
        roles = Set.of("ROLE_ADMIN");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(1, userDtoList.size());

        roles = Set.of("ROLE_ADMIN", "ROLE_USER");
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(5, userDtoList.size());

        roles = null;
        balanceEqual = 0.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(1, userDtoList.size());

        balanceEqual = 10000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(1, userDtoList.size());

        balanceEqual = null;
        balanceMoreOrEqual = 20000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(3, userDtoList.size());

        balanceMoreOrEqual = null;
        balanceLessOrEqual = 10000.0;
        userDtoList = service.searchUsers(usersId, usernames, emails, roles,
                balanceEqual, balanceMoreOrEqual, balanceLessOrEqual);
        assertEquals(2, userDtoList.size());
    }

}
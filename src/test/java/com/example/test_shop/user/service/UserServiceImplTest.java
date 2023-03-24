package com.example.test_shop.user.service;

import com.example.test_shop.user.dto.NewUserDto;
import com.example.test_shop.user.dto.UserAdminUpdateDto;
import com.example.test_shop.user.dto.UserDto;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class UserServiceImplTest {

    private final UserService service;
    private final UserRepository repository;

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
        Assertions.assertEquals(6, userDto.getId());
        Assertions.assertEquals("TestName_6", userDto.getUsername());
        Assertions.assertNotNull(userDto.getPassword());
        Assertions.assertEquals("Test_6@email.ru", userDto.getEmail());
        Assertions.assertEquals("ROLE_USER", userDto.getRole());
        Assertions.assertEquals("ACTIVE", userDto.getStatus());
    }

    @Test
    void update() {
        Long userForUpdateId = 2L;
        UserAdminUpdateDto updateDto = UserAdminUpdateDto.builder()
                .username("TestName_2(updated)")
                .password("TestPassword_2(updated)")
                .email("Test_2(updated)@email.ru")
                .balance(15000.0)
                .status("BLOCKED")
                .build();
        UserDto updatedUserDto = service.update(updateDto, userForUpdateId);
        Assertions.assertEquals(2, updatedUserDto.getId());
        Assertions.assertEquals("TestName_2(updated)", updatedUserDto.getUsername());
        Assertions.assertNotNull(updatedUserDto.getPassword());
        Assertions.assertEquals("Test_2(updated)@email.ru", updatedUserDto.getEmail());
        Assertions.assertEquals("ROLE_USER", updatedUserDto.getRole());
        Assertions.assertEquals("BLOCKED", updatedUserDto.getStatus());
    }

    @Test
    void delete() {
        Long userIdForDelete = 2L;
        int beforeDeleteUsersQuantity = repository.findAll().size();
        service.delete(userIdForDelete);
        int afterDeleteUsersQuantity = repository.findAll().size();
        Assertions.assertEquals(beforeDeleteUsersQuantity-1, afterDeleteUsersQuantity);
    }

    @Test
    void getOwnAccount() {
    }

    @Test
    void getAnotherUser() {
    }

    @Test
    void searchUsers() {
    }
}
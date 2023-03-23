package com.example.test_shop.user.dto;

import com.example.test_shop.user.model.User;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for admin update information about new {@link User}
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class UserAdminUpdateDto {

    @Email(message = "Incorrect email")
    private String email;
    private String username;
    private String password;

    @Digits(integer = 10, fraction = 2)
    private Double balance;
    private String status;

}

package com.example.test_shop.user.dto;

import com.example.test_shop.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link User}
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewUserDto {

    @NotBlank(message = "Username can't be blank")
    private String username;

    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @NotBlank(message = "Role can't be blank")
    private String role;
}
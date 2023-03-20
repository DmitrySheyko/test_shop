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

    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}

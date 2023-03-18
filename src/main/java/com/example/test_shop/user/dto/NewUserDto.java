package com.example.test_shop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserDto {

    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

}

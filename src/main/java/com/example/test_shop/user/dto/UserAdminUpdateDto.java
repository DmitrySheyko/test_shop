package com.example.test_shop.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAdminUpdateDto {

    @Email
    private String email;
    private String username;
    private String password;
    private Double balance;
    private String status;

}

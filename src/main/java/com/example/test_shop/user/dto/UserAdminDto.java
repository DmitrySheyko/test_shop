package com.example.test_shop.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAdminDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Double balance;
    private String status;

}

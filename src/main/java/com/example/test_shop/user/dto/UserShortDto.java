package com.example.test_shop.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserShortDto {

    private Long id;
    private String username;
    private String email;

}

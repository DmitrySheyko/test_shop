package com.example.test_shop.user.dto;

import com.example.test_shop.user.model.User;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for admin getting information about new {@link User}
 *
 * @author DmitrySheyko
 */
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

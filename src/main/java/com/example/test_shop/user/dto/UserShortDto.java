package com.example.test_shop.user.dto;

import com.example.test_shop.user.model.User;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for getting minimal information about new {@link User}
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class UserShortDto {

    private Long id;
    private String username;
    private String email;

}

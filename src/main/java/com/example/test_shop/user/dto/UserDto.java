package com.example.test_shop.user.dto;

import com.example.test_shop.company.dto.CompanyShortDto;
import com.example.test_shop.notification.dto.NotificationDto;
import com.example.test_shop.user.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class of dto for admin getting information about new {@link User}
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Double balance;
    private String status;
    private String role;
    private List<CompanyShortDto> companiesList;
    private List<NotificationDto> notificationsList;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
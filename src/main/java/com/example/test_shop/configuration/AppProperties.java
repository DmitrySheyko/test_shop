package com.example.test_shop.configuration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class of external application properties
 *
 * @author DmitrySheyko
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.data")
public class AppProperties {

    // Количество дней, в течение которого покупатель может осуществить возврат товара
    @Positive
    private Integer daysForReturnProducts;

    // размер комиссии магазина
    @PositiveOrZero
    private Double commissionOfShop;

    // Имя администратора
    @NotBlank
    private String adminUsername;

    // Пароль администратора
    @NotBlank
    private String adminPassword;

    // Email администратора
    @Email
    private String adminEmail;

}
package com.example.test_shop.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.data")
public class AppProperties {

    private Integer daysForReturnProducts;
    private Float commissionOfShop;

}
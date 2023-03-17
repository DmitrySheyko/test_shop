package com.example.test_shop.rate.model;

import com.example.test_shop.product.model.Product;
import com.example.test_shop.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Rate {

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "user_id")
    private User user;

    @Column(name = "product_id")
    private Product product;
}

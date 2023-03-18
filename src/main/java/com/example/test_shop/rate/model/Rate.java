package com.example.test_shop.rate.model;

import com.example.test_shop.product.model.Product;
import com.example.test_shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "product_id")
    @ManyToOne
    private Product product;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

}

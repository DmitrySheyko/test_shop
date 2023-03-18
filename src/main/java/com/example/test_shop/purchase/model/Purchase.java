package com.example.test_shop.purchase.model;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "company_id")
    @ManyToOne
    private Company company;

    @Column(name = "seller_id")
    @ManyToOne
    private User seller;

    @Column(name = "buyer_id")
    @ManyToOne
    private User buyer;

    @Column(name = "product_id")
    @ManyToOne
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_for_unit")
    private Double priceForUnit;

    @Column(name = "total_sum")
    private Double totalSum;

    @Column(name = "shop_commission_sum")
    private Double shopCommission;

    @Column(name = "purchase_date_time")
    private LocalDateTime purchaseDateTime;

}

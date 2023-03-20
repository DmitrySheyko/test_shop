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

    @Column(name = "type_of_purchase")
    @Enumerated(EnumType.STRING)
    private PurchaseType type;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
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

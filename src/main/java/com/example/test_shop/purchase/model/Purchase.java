package com.example.test_shop.purchase.model;

import com.example.test_shop.company.model.Company;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Class of {@link Purchase} entity
 *
 * @author DmitrySheyko
 */
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
    private Long id;

    @Column(name = "type_of_purchase")
    @Enumerated(EnumType.STRING)
    private PurchaseType type;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // Для сохранения информации при удвлении организации
    @Column(name = "company_id_copy")
    private Long companyId;

    // Для сохранения информации при удвлении организации
    @Column(name = "company_name")
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Для сохранения информации при удвлении организации
    @Column(name = "product_id_copy")
    private Long productId;

    // Для сохранения информации при удвлении организации
    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_for_unit")
    private Double priceForUnit;

    @Column(name = "total_sum_without_discount")
    private Double totalSumWithoutDiscount;

    @Column(name = "total_sum_with_discount")
    private Double totalSumWithDiscount;

    @Column(name = "shop_commission_sum")
    private Double shopCommissionSum;

    @Column(name = "discount_sum")
    private Double discountSum;

    @Column(name = "seller_income_sum")
    private Double sellerIncomeSum;

    @Column(name = "purchase_date_time")
    @CreationTimestamp
    private LocalDateTime purchaseDateTime;

    @Column(name = "is_rejected")
    private boolean isRejected;

    @Column(name = "reject_for_purchase_id")
    private Long rejectForPurchaseId;

    @Column(name = "rejection_id")
    private Long rejectionId;

}
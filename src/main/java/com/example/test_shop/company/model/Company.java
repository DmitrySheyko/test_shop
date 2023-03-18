package com.example.test_shop.company.model;

import com.example.test_shop.product.model.Product;
import com.example.test_shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_id")
    @ManyToOne
    private User owner;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "company_status")
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    private Set<Product> productsSet;

}

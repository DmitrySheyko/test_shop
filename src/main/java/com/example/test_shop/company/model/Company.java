package com.example.test_shop.company.model;

import com.example.test_shop.product.model.Product;
import com.example.test_shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class of {@link Company} entity
 *
 * @author DmitrySheyko
 */
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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "company_status")
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    @OneToMany (mappedBy = "company")
    private Set<Product> productsSet = new HashSet<>();

}
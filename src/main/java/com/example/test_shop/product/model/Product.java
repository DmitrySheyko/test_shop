package com.example.test_shop.product.model;

import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.rate.model.Rate;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Class of {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    @OneToMany(mappedBy = "product")
    private Set<Comment> commentsSet = new HashSet<>();

    @Column(name = "key_words")
    private String keyWords;

    @Column(name = "characteristics")
    private String characteristics;

    @OneToMany(mappedBy = "product")
    private Set<Rate> ratesSet = new HashSet<>();

}
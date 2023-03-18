package com.example.test_shop.product.model;

import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.discount.Discount;
import com.example.test_shop.rate.model.Rate;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Companies")
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

    @Column(name = "company_id")
    @ManyToOne
    private Company company;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount_id")
    @ManyToOne
    private Discount discount;

    @OneToMany
    private Set<Comment> commentsSet;

    @Column(name = "key_words")
    private String keyWords;

    @Column(name = "characteristics")
    private String characteristics;

    @OneToMany
    private Set<Rate> ratesSet;

}

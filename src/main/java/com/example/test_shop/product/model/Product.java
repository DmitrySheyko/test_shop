package com.example.test_shop.product.model;

import com.example.test_shop.comment.Comment;
import com.example.test_shop.company.Company;
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
    private Company company;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount_id")
    private Discount discount;

    @Column(name = "")
    private Set<Comment> commentsSet;

    @Column(name = "")
    private Set<String> keyWordsSet;

    @Column(name = "")
    private String characteristics;

    @Column(name = "")
    private Set<Rate> ratesSet;
}

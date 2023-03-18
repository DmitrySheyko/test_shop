package com.example.test_shop.product.controller;

import com.example.test_shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/product")
public class productUserController {

    private final ProductService service;

    // Добавление комментария о продукте

    // Добавление оценки


}

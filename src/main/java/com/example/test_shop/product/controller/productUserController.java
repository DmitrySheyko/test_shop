package com.example.test_shop.product.controller;

import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class productUserController {

    private final ProductService service;

    // Добавление нового товара
    @PostMapping("/{id}/product")
    public ProductDto add(@Valid @RequestBody NewProductDto productDto,
                          @Positive @PathVariable(value = "id") Long userId){
        return service.add(productDto, userId);
    }



}

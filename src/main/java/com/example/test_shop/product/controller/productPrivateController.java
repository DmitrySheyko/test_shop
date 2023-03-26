package com.example.test_shop.product.controller;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.product.dto.NewProductDto;
import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.product.dto.ProductUpdateDto;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Class of user's controller for {@link Product} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/product")
public class productPrivateController {

    private final ProductService service;

    // Добавление нового товара
    @PostMapping
    public ProductDto add(@Valid @RequestBody NewProductDto productDto,
                          @Positive @PathVariable(value = "userId") Long userId) {
        return service.add(productDto, userId);
    }

    // Изменение товара
    @PatchMapping
    public ProductDto update(@Valid @RequestBody ProductUpdateDto productDto,
                             @Positive @PathVariable(value = "userId") Long userId) {
        return service.update(productDto, userId);
    }

    // Подключение скидки (discountId) списку товаров (productsId)
    @PatchMapping("/setDiscount")
    public List<ProductDto> setDiscountForProductDet(
            @RequestParam(value = "discountId") Long discountId,
            @RequestParam(value = "productsId") Set<Long> productsSet,
            @Positive @PathVariable(value = "userId") Long userId) {
        return service.setDiscountForProductDet(discountId, productsSet, userId);
    }


    // Удаление товара
    @DeleteMapping("{productId}")
    public ProductDto delete(@Positive @PathVariable(value = "userId") Long userId,
                             @Positive @PathVariable(value = "productId") Long productId) {
        return service.delete(userId, productId);
    }

    // Получение списка всех товаров  имеющих статус ACTIVE
    @GetMapping
    public List<ProductShortDto> getAll(@Positive @PathVariable(value = "userId") Long userId,
                                        @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                        @RequestParam(value = "size", defaultValue = "10") @Min(1) Integer size) {
        return service.getAllActive(userId, from, size);
    }

    // Получе коментариев о товаре
    @GetMapping("{productId}")
    public List<CommentDto> getComments(@Positive @PathVariable(value = "userId") Long userId,
                                        @Positive @PathVariable(value = "productId") Long productId) {
        return service.getComments(userId, productId);
    }

}
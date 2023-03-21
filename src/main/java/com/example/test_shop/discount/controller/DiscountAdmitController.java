package com.example.test_shop.discount.controller;

import com.example.test_shop.discount.dto.DiscountAdminUpdateDto;
import com.example.test_shop.discount.dto.DiscountDto;
import com.example.test_shop.discount.dto.NewDiscountDto;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.discount.service.DiscountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Class of controller for {@link Discount} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/discount")
public class DiscountAdmitController {

    private final DiscountService service;

    // Создать новую скидку
    @PostMapping
    public DiscountDto add(@Valid @RequestBody NewDiscountDto discountDto) {
        return service.add(discountDto);
    }

    // Обновить скидку
    @PatchMapping("/{discountId}")
    public DiscountDto update(@Valid @RequestBody DiscountAdminUpdateDto discountDto,
                              @Positive @PathVariable(value = "discountId") Long discountId) {
        return service.update(discountDto, discountId);
    }

    // Удалить скидку из базы и из всех товаров
    @DeleteMapping("/{discountId}")
    public String delete(@Positive @PathVariable(value = "discountId") Long discountId) {
        return service.delete(discountId);
    }

}

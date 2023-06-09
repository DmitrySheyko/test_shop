package com.example.test_shop.discount.service;

import com.example.test_shop.discount.dto.DiscountAdminUpdateDto;
import com.example.test_shop.discount.dto.DiscountDto;
import com.example.test_shop.discount.dto.NewDiscountDto;
import com.example.test_shop.discount.model.Discount;

/**
 * Interface of service for {@link Discount} entity
 *
 * @author DmitrySheyko
 */
public interface DiscountService {

    DiscountDto add(NewDiscountDto discountDto);

    DiscountDto update(DiscountAdminUpdateDto discountDto, Long discountId);

    String delete(Long discountId);

}

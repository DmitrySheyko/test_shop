package com.example.test_shop.product.service;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.product.dto.NewProductDto;
import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.product.dto.ProductUpdateDto;
import com.example.test_shop.product.model.Product;

import java.util.List;
import java.util.Set;

/**
 * Interface of service for {@link Product} entity
 *
 * @author DmitrySheyko
 */
public interface ProductService {

    ProductDto add(NewProductDto productDto, Long userId);

    ProductDto update(ProductUpdateDto productDto, Long userId);

    String delete(Long userId, Long productId);

    List<ProductShortDto> getAll(Long userId, Integer from, Integer size);

    Set<CommentDto> getComments(Long userId, Long productId);

}

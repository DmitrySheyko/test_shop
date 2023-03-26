package com.example.test_shop.product.service;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.company.service.CompanyService;
import com.example.test_shop.product.dto.NewProductDto;
import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.dto.ProductUpdateDto;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations = classpath:data-test.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ProductServiceImplTest {

    private final ProductService service;
    private final ProductRepository repository;
    private final CompanyService companyService;

    @Test
    void add() {
        Long userId = 1L;
        Long companyId = 1L;
        int quantityOfProductsBeforeAdd = repository.findAll().size();
        int quantityOfProductsAfterAdd = quantityOfProductsBeforeAdd + 1;

        NewProductDto newProductDto = NewProductDto.builder()
                .name("TestProduct_3")
                .description("TestDescription_3")
                .companyId(companyId)
                .price(100.00)
                .quantity(10)
                .discountId(null)
                .keyWords("TestWords_3")
                .characteristics("TestCharacteristics_3")
                .build();
        ProductDto productDto = service.add(newProductDto, userId);

        assertEquals(quantityOfProductsAfterAdd, repository.findAll().size());

        assertEquals("TestProduct_3", productDto.getName());
        assertEquals("TestDescription_3", productDto.getDescription());
        assertEquals(100.0, productDto.getPrice().byteValue());
        assertEquals(10, productDto.getQuantity());
        assertEquals("TestWords_3", productDto.getKeyWords());
        assertEquals("TestCharacteristics_3", productDto.getCharacteristics());
        assertEquals(companyId, productDto.getCompany().getId());
    }

    @Test
    void update() {
        Long userId = 1L;
        Long productId = 1L;
        ProductUpdateDto updateDto = ProductUpdateDto.builder()
                .id(productId)
                .name("TestProduct_1(update)")
                .description("TestDescription_1(update)")
                .price(110.0)
                .quantity(11)
                .discountId(null)
                .keyWords("TestWords_3(update)")
                .characteristics("TestCharacteristics_1(update)")
                .build();
        ProductDto updatedProductDto = service.update(updateDto, userId);
        assertEquals("TestProduct_1(update)", updatedProductDto.getName());
        assertEquals("TestDescription_1(update)", updatedProductDto.getDescription());
        assertEquals(110.0, updatedProductDto.getPrice());
        assertEquals(11, updatedProductDto.getQuantity());
        assertEquals("TestWords_3(update)", updatedProductDto.getKeyWords());
        assertEquals("TestCharacteristics_1(update)", updatedProductDto.getCharacteristics());

        updateDto = ProductUpdateDto.builder()
                .id(productId)
                .status("BLOCKED")
                .build();
        updatedProductDto = service.update(updateDto, userId);
        assertEquals("BLOCKED", updatedProductDto.getSatus());
    }

    @Test
    void delete() {
        Long userId = 1L;
        Long productId = 1L;
        service.delete(userId, productId);
        Product product = repository.findById(productId).get();
        assertEquals("DELETED", product.getStatus().name());
        assertTrue(repository.existsById(productId));
    }

    @Test
    void getAllActive() {
        int from = 0;
        int size = 10;
        Long userId = 1L;
        Long productId = 1L;
        Long companyId = 1L;

        // Проверяем количество активных товаров
        assertEquals(4, service.getAllActive(userId, from, size).size());

        // Проверяем работает ли пагинация
        size = 1;
        Assertions.assertEquals(1, service.getAllActive(userId, from, size).size());

        size = 10;
        // Удадляем один товар и снова проверяем количество активных товаров
        service.delete(userId, productId);
        assertEquals(3, service.getAllActive(userId, from, size).size());

        // Удадляем компанию и снова проверяем количество активных товаров
        companyService.delete(companyId);
        Assertions.assertEquals(0, service.getAllActive(userId, from, size).size());
    }

    @Test
    void getComments() {
        Long userId = 1L;
        Long productId = 1L;
        List<CommentDto> commentDtoLost = service.getComments(userId, productId);
        assertEquals("Test comment text_1", commentDtoLost.get(0).getText());
    }

}
package com.example.test_shop.discount.service;

import com.example.test_shop.discount.dto.DiscountAdminUpdateDto;
import com.example.test_shop.discount.dto.DiscountDto;
import com.example.test_shop.discount.dto.NewDiscountDto;
import com.example.test_shop.discount.repository.DiscountRepository;
import com.example.test_shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(properties = "spring.sql.init.data-locations=classpath:data-test.sql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DiscountServiceImplTest {

    private final DiscountService service;
    private final DiscountRepository repository;
    private final ProductRepository productRepository;
    private final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void add() {
        int quantityOfDiscountsBeforeAdd = repository.findAll().size();
        NewDiscountDto newDiscountDto = NewDiscountDto.builder()
                .description("TestDiscount_1")
                .value(0.2)
                .startDateTime("2024-01-01 10:10:10")
                .finishDateTime("2024-04-01 10:10:10")
                .build();
        DiscountDto discountDto = service.add(newDiscountDto);
        assertEquals("TestDiscount_1", discountDto.getDescription());
        assertEquals(0.2, discountDto.getValue());
        assertEquals(LocalDateTime.parse("2024-01-01 10:10:10", DATE_TIME_PATTERN), discountDto.getStartDateTime());
        assertEquals(LocalDateTime.parse("2024-04-01 10:10:10", DATE_TIME_PATTERN), discountDto.getFinishDateTime());
        assertEquals(quantityOfDiscountsBeforeAdd + 1, repository.findAll().size());
    }


    @Test
    void update() {
        NewDiscountDto newDiscountDto = NewDiscountDto.builder()
                .description("TestDiscount_1")
                .value(0.2)
                .startDateTime("2024-01-01 10:10:10")
                .finishDateTime("2024-04-01 10:10:10")
                .build();
        DiscountDto discountDto = service.add(newDiscountDto);
        assertEquals("TestDiscount_1", discountDto.getDescription());
        assertEquals(0.2, discountDto.getValue());
        assertEquals(LocalDateTime.parse("2024-01-01 10:10:10", DATE_TIME_PATTERN), discountDto.getStartDateTime());
        assertEquals(LocalDateTime.parse("2024-04-01 10:10:10", DATE_TIME_PATTERN), discountDto.getFinishDateTime());

        DiscountAdminUpdateDto updateDto = DiscountAdminUpdateDto.builder()
                .description("TestDiscount_1(updated)")
                .value(0.3)
                .startDateTime("2025-01-01 10:10:10")
                .finishDateTime("2025-04-01 10:10:10")
                .build();
        discountDto = service.update(updateDto, discountDto.getId());
        assertEquals("TestDiscount_1(updated)", discountDto.getDescription());
        assertEquals(0.3, discountDto.getValue());
        assertEquals(LocalDateTime.parse("2025-01-01 10:10:10", DATE_TIME_PATTERN), discountDto.getStartDateTime());
        assertEquals(LocalDateTime.parse("2025-04-01 10:10:10", DATE_TIME_PATTERN), discountDto.getFinishDateTime());
    }

    @Test
    void delete() {
        int quantityOfDiscountsBeforeAdd = repository.findAll().size();
        NewDiscountDto newDiscountDto = NewDiscountDto.builder()
                .description("TestDiscount_1")
                .value(0.2)
                .startDateTime("2024-01-01 10:10:10")
                .finishDateTime("2024-04-01 10:10:10")
                .build();
        DiscountDto discountDto = service.add(newDiscountDto);
        assertEquals(quantityOfDiscountsBeforeAdd + 1, repository.findAll().size());

        service.delete(discountDto.getId());
        assertEquals(quantityOfDiscountsBeforeAdd, repository.findAll().size());
        assertFalse(repository.existsById(discountDto.getId()));
    }

}
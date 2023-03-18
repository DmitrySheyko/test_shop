package com.example.test_shop.comment.dto;

import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private String id;
    private String text;
    private UserShortDto user;
    private ProductShortDto product;
    private LocalDateTime createdOn;

}

package com.example.test_shop.comment.dto;

import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.user.dto.UserShortDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class of dto for getting information about {@link Comment} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class CommentDto {

    private Long id;
    private String text;
    private UserShortDto user;
    private ProductShortDto product;
    private LocalDateTime createdOn;

}

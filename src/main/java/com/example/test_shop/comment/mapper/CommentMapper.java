package com.example.test_shop.comment.mapper;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.dto.NewCommentDto;
import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.product.mapper.ProductMapper;
import com.example.test_shop.user.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Class of mapper for {@link Comment} entity
 *
 * @author DmitrySheyko
 */
@Component
public class CommentMapper {

    public static Comment toComment(NewCommentDto commentDto) {
        if (commentDto == null) {
            return null;
        } else {
            return Comment.builder()
                    .text(commentDto.getText())
                    .build();
        }
    }

    public static CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        } else {
            return CommentDto.builder()
                    .id(comment.getId())
                    .user(UserMapper.toUserShortDto(comment.getUser()))
                    .product(ProductMapper.toShortDto(comment.getProduct()))
                    .text(comment.getText())
                    .createdOn(comment.getCreatedOn())
                    .build();
        }
    }

}

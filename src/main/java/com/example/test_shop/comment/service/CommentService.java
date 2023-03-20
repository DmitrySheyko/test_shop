package com.example.test_shop.comment.service;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.dto.NewCommentDto;
import com.example.test_shop.comment.model.Comment;

/**
 * Interface of service for {@link Comment} entity
 *
 * @author DmitrySheyko
 */
public interface CommentService {

    CommentDto add(NewCommentDto commentDto, Long userId);

    String delete(Long userId, Long commentId);

}

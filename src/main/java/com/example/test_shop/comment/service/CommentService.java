package com.example.test_shop.comment.service;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.dto.NewCommentDto;

public interface CommentService {
    CommentDto add(NewCommentDto commentDto, Long userId);
}

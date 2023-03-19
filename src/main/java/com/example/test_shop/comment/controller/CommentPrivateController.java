package com.example.test_shop.comment.controller;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.dto.NewCommentDto;
import com.example.test_shop.comment.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/comment")
public class CommentPrivateController {

    private final CommentService service;

    @PostMapping("/{id}")
    public CommentDto add(@Valid @RequestBody NewCommentDto commentDto,
                          @Positive @PathVariable(value = "id") Long userId) {
        return service.add(commentDto, userId);
    }

}

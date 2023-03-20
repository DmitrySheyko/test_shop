package com.example.test_shop.comment.controller;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.dto.NewCommentDto;
import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.comment.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Class of controller for {@link Comment} entity
 *
 * @author DmitrySheyko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/{userId}/comment")
public class CommentPrivateController {

    private final CommentService service;

    // Добавление комментария
    @PostMapping
    public CommentDto add(@Valid @RequestBody NewCommentDto commentDto,
                          @Positive @PathVariable(value = "userId") Long userId) {
        return service.add(commentDto, userId);
    }

    // Удаление комментария
    @DeleteMapping("/{commentId}")
    public String delete(@Positive @PathVariable(value = "userId") Long userId,
                         @Positive @PathVariable(value = "userId") Long commentId) {
        return service.delete(userId, commentId);
    }

}

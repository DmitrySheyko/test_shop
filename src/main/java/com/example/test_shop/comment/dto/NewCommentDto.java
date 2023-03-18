package com.example.test_shop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class NewCommentDto {

    @NotBlank
    private String text;

    @NotNull
    @Positive
    private long userId;

    @NotNull
    @Positive
    private long productId;

}

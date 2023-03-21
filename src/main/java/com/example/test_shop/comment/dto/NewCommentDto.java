package com.example.test_shop.comment.dto;

import com.example.test_shop.comment.model.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

/**
 * Class of dto for creating new {@link Comment} entity
 *
 * @author DmitrySheyko
 */
@Data
@Builder
public class NewCommentDto {

    @NotBlank
    private String text;

    @NotNull
    @Positive
    private long productId;

}

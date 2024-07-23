package com.example.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 작성을 위한 DTO
 */
@Getter
@NoArgsConstructor
public class CommentRequest {
    @NotBlank
    private Long postId;

    @NotBlank
    private String text;
}

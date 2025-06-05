package com.example.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 게시글을 작성하기 위한 DTO
 */
@Getter
@NoArgsConstructor
public class PostRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String createdAt;
}

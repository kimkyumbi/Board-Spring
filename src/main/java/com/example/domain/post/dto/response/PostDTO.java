package com.example.domain.post.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 전체 게시글 조회를 위한 DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String createdAt;
    private int visit;
}

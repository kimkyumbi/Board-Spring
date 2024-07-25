package com.example.domain.post.dto.response;

import lombok.*;

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
    private int visit;
}

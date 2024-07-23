package com.example.domain.post.dto.response;

import com.example.domain.comment.dto.response.CommentResponse;
import lombok.*;

import java.util.List;

/**
 * 특정 게시글을 조회하기 위한 DTO
 */
@Getter
@Builder
public class GetPostResponse {
    private String title;
    private String content;
    private int visit;
    private List<CommentResponse> comments;
}

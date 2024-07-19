package com.example.domain.post.dto.response;

import com.example.domain.comment.dto.response.CommentResponse;
import com.example.domain.comment.entity.CommentEntity;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetPostResponse {
    private String title;
    private String content;
    private int visit;
    private List<CommentResponse> comments;
}

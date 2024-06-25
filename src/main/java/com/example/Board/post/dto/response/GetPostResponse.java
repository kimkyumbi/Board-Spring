package com.example.Board.post.dto.response;

import com.example.Board.comment.entity.CommentEntity;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class GetPostResponse {
    private String title;
    private String content;
    private int visit;
    private List<CommentEntity> comments;
}

package com.example.Board.post.dto.response;

import lombok.*;

@Getter
@Builder
public class GetPostResponse {
    private String title;
    private String content;
    private int visit;
}

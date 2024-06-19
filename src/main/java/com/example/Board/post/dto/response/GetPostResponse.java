package com.example.Board.post.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class GetPostResponse {
    private String title;
    private String content;
    private int visit;
}

package com.example.Board.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchPostRequest {
    private String title;
    private String content;
}

package com.example.Board.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchPostRequest {

    @NotBlank(message = "글의 제목을 빈칸으로 수정할 수 없습니다.")
    private String title;

    @NotBlank(message = "글의 내용을 빈칸으로 수정할 수 없습니다.")
    private String content;
}

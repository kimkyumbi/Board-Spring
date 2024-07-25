package com.example.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 수정을 위한 DTO
 */
@Getter
@NoArgsConstructor
public class PatchPostRequest {

    @NotBlank(message = "글의 제목을 빈칸으로 수정할 수 없습니다.")
    private String title;

    @NotBlank(message = "글의 내용을 빈칸으로 수정할 수 없습니다.")
    private String content;
}

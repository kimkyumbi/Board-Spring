package com.example.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}

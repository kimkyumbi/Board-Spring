package com.example.domain.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,32}$",
            message = "비밀번호는 8~32자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;
}

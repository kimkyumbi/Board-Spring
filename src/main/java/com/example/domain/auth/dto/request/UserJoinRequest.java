package com.example.domain.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청을 위한 DTO
 */
@Getter
@NoArgsConstructor
public class UserJoinRequest {
    // 클라이언트가 사용할 email
    private String email;

    // 정규식
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,32}$",
            message = "비밀번호는 8~32자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    // 클라이언트가 사용할 비밀번호
    private String password;
}

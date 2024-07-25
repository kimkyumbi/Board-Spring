package com.example.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청을 위한 DTO
 */
@Getter
@NoArgsConstructor
public class UserLoginRequest {
    // 클라이언트가 로그인 시 사용하는 이메일
    private String email;

    // 클라이언트가 로그인 시 사용하는 비밀번호
    private String password;
}

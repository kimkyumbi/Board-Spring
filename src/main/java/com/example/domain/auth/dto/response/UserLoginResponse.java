package com.example.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유지가 로그인 시 응답을 위한 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    // 로그인했을 때 발급될 accessToken
    private String accessToken;
    // 로그인했을 때 발급될 refreshToken
    private String refreshToken;
}

package com.example.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토큰을 재발급 응답을 위한 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenReissueDTO {
    // 새로 발급된 accessToken
    private String accessToken;
    // 새로 발급된 refreshToken
    private String refreshToken;
}

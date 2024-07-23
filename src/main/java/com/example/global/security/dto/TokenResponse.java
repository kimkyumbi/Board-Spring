package com.example.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 토큰을 반환하기 위한 DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}

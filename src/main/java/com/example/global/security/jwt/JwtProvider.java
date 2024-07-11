package com.example.global.security.jwt;

import com.example.global.security.dto.TokenResponse;
import com.example.global.security.principle.AuthDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final AuthDetailsService authDetailsService;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    @Value("${spring.jwt.refreshKey")
    private String refreshKey;
    @Value("${spring.jwt.accessExp}")
    public Long accessExp;
    @Value("${spring.jwt.refreshExp}")
    public Long refreshExp;

    private static final String ACCESS_KEY = "accessToken";
    private static final String REFRESH_KEY = "refreshToken";

    public String generateAccessToken(Long userId, long expiration) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(String.valueOf(userId))
                .setHeaderParam("typ", ACCESS_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String generateRefreshToken(Long userId, long expiration) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, refreshKey)
                .setSubject(String.valueOf(userId))
                .setHeaderParam("typ", REFRESH_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public TokenResponse getToken(Long userId) {
        String accessToken = generateAccessToken(userId, accessExp);
        String refreshToken = generateRefreshToken(userId, refreshExp);

        return new TokenResponse(accessToken, refreshToken);
    }
}

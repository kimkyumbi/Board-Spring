package com.example.global.security.jwt;

import com.example.global.exception.TokenException;
import com.example.global.security.dto.TokenResponse;
import com.example.global.security.principle.AuthDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final AuthDetailsService authDetailsService;

    // application.yml에 환경변수로 만들어둔 키값을 가져옴
    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    @Value("${spring.jwt.refreshKey")
    private String refreshKey;
    @Value("${spring.jwt.accessExp}")
    public Long accessExp;
    @Value("${spring.jwt.refreshExp}")
    public Long refreshExp;

    private static final String ACCESS_KEY = "accessToken"; // accessToken을 식별하기 위한 상수
    private static final String REFRESH_KEY = "refreshToken"; // refreshToken을 식별하기 위한 상수

    // 액세스 토큰을 생성하는 메서드
    private String generateAccessToken(Long userId, long expiration) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey) // HS256 알고리즘을 사용하여 서명, 서명 키는 secretKey
                .setSubject(String.valueOf(userId)) // 토큰의 Subject 클레임에 userId를 문자열로 변환하여 설정
                .setHeaderParam("typ", ACCESS_KEY) // 토큰의 헤더에 typ 이라는 커스텀 파라미터를 추가하고 값은 ACCESS_KEY로 설정
                .setIssuedAt(new Date()) // 토큰이 생성된 시간을 "iat" 클레임으로 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 토큰의 만료 시간을"exp 클레임으로 설정
                .compact(); // JWT를 JSON 객체로 생성한 후, Base64Url 인코딩하여 문자열로 변환
    }

    // 리프레시 토큰을 생성하는 메서드
    private String generateRefreshToken(Long userId, long expiration) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, refreshKey) // HS256 알고리즘을 사용하여 서명, 서명 키는 refreshKey
                .setSubject(String.valueOf(userId)) // 토큰의 Subject 클레임에 userId를 문자열로 변환하여 설정
                .setHeaderParam("typ", REFRESH_KEY) // 토큰의 헤더에 typ 이라는 커스텀 파라미터를 추가하고 값은 REFRESH_KEY로 설정
                .setIssuedAt(new Date()) // 토큰이 생성된 시간을 "iat" 클레임으로 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 토큰의 만료 시간을 exp 클레임으로 설정
                .compact(); // JWT를 JSON 객체로 생성한 후, Base64Url 인코딩하여 문자열로 변환

    }

    // 토큰을 생성하고 TokenResponse 객체를 반환하는 메서드
    public TokenResponse getToken(Long userId) {

        // generateAccessToken 메서드를 호출하여 액세스 토큰을 생성하고 accessToken 변수에 저장
        String accessToken = generateAccessToken(userId, accessExp);

        // generateRefreshToken 메서드를 호출하여 리프레시 토큰을 생성하고 refreshToken 변수에 저장
        String refreshToken = generateRefreshToken(userId, refreshExp);

        // 생성된 액세스 토큰과 리프레시 토큰을 TokenResponse 객체에 담아 반환
        return new TokenResponse(accessToken, refreshToken);
    }

    // 토큰 검증 메서드
    public boolean validateToken(String token, String secret) {
        try {
            // 서명에 사용될 키를 설정하고 토큰을 파싱하고 Signature를 검증해 claim 추출
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 때 발생하는 예외 처리
            throw new TokenException("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) { // 검증되지 않은 토큰일 때 발생하는 예외 처리
            throw new TokenException("검증되지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    // Authentication 객체 생성 메서드
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // AuthDetailsService의 loadUserByUsername 메서드를 사용하여 토큰의 Subject를 기반으로 UserDetails를 가져옴
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));

        // UsernamePasswordAuthenticationToken 객체를 생성하고, userDetails를 포함시킴
        // credentials는 null로 설정하고, userDetails에서 권한(authorities)를 가져와 설정함
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 파라미터로 받은 토큰과 서명을 위한 refreshKey로 Token의 sub claim을 가져오는 메서드
    public String getRefreshTokenUserId(String token) {
        // getTokenBody 메서드를 호출하여 토큰의 sub 클레임을 추출하고, 사용자 Id를 문자열로 변환
        return getTokenBody(token, refreshKey).get("sub", String.class);
    }

    // 파라미터로 받은 subject와 서명을 위한 secretKey로 토큰의 subject를 추출하는 메서드
    private String getTokenSubject(String subject) {
        // getTokenBody 메서드를 호출하여 토큰의 클레임을 추출하고 클레임에서 subject를 반환
        return getTokenBody(subject, secretKey).getSubject();
    }

    // 토큰의 Claims 객체를 생성해 Body를 추출하는 메서드
    private Claims getTokenBody(String token, String secret) {
        try {
           // 서명에 사용할 key를 설정하고 토큰의 모든 claim을 추출하는 Claims 객체 생성 후 토큰의 body를 추출함
           return Jwts.parserBuilder()
                   .setSigningKey(secret)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 때 발생하는 예외 처리
            throw new TokenException("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) { // 토큰이 검증되지 않았을 때 발생하는 예외 처리
            throw new TokenException("검증되지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}

package com.example.global.security.jwt;

import com.example.domain.auth.dto.response.UserLoginResponse;
import com.example.domain.user.enums.TokenType;
import com.example.global.exception.ExpectedException;
import com.example.global.exception.TokenException;
import com.example.global.security.dto.TokenResponse;
import com.example.global.security.principle.AuthDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

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

    public UserLoginResponse generateTokenSet(UUID id){
        return new UserLoginResponse(
                generateToken(id, TokenType.ACCESS_TOKEN),
                generateToken(id, TokenType.REFRESH_TOKEN)
        );
    }

    public String generateToken(UUID id, TokenType tokenType) {
        Long expired = tokenType == TokenType.ACCESS_TOKEN ? accessExp : refreshExp;

        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .signWith(signingKey)
                .subject(String.valueOf(id))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expired))
                .compact();
    }

    // 토큰 검증 메서드
    public Boolean validateToken(String token){
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    // Authentication 객체 생성 메서드
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        // AuthDetailsService의 loadUserByUsername 메서드를 사용하여 토큰의 Subject를 기반으로 UserDetails를 가져옴
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));

        // UsernamePasswordAuthenticationToken 객체를 생성하고, userDetails를 포함시킴
        // credentials는 null로 설정하고, userDetails에서 권한(authorities)를 가져와 설정함
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 파라미터로 받은 subject와 서명을 위한 secretKey로 토큰의 subject를 추출하는 메서드
    private String getTokenSubject(String subject) {
        // getTokenBody 메서드를 호출하여 토큰의 클레임을 추출하고 클레임에서 subject를 반환
        return getClaims(subject).getSubject();
    }

    // 토큰에서 Claims 객체를 추출하는 메서드
    public Claims getClaims(String token) {
        // Base64 인코더를 사용하여 secretKey를 바이트 배열로 변환
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());

        // secretKey 바이트 배열을 사용하여 HMAC-SHA 키 객체 생성
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        try {
            return Jwts.parser() // JWT 토큰을 파싱하고 Claims 객체를 추출
                    .verifyWith(signingKey) // 서명 키 설정
                    .build() // 파서 빌드
                    .parseSignedClaims(token) // 토큰 파싱 및 Claims JWS 얻기
                    .getPayload(); // Claims 객체의 Body 부분 추출
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 예외 처리
            throw new ExpectedException("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 형식의 JWT 토큰인 경우 예외 처리
            throw new ExpectedException("형식이 일치하지 않는 토큰입니다.", HttpStatus.FORBIDDEN);
        } catch (MalformedJwtException e) {
            // 구성 요소가 잘못된 JWT 토큰인 경우 예외 처리
            throw new ExpectedException("올바르지 않은 구성의 토큰입니다.", HttpStatus.FORBIDDEN);
        } catch (SignatureException e) {
            // 서명 검증에 실패한 JWT 토큰인 경우 예외 처리
            throw new ExpectedException("서명을 확인할 수 없는 토큰입니다.", HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            // 기타 런타임 예외 발생 시 예외 처리
            throw new ExpectedException("알 수 없는 토큰입니다.", HttpStatus.FORBIDDEN);
        }
    }
}

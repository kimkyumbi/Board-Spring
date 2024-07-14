package com.example.global.security.filter;

import com.example.global.security.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    // OncePerRequestFilter의 doFilterInternal 메서드 오버라이드
    @Override
    // 요청을 가로채서 로직 수행
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization으로 헤더에 JWT 토큰을 가져와 token에 저장
        String token = request.getHeader("Authorization");

        if (token != null) { // 토큰이 null이 아니면 수행
            // resolveToken 메서드로 순수한 토큰 값 추출
            token = resolveToken(token);
            // jwtProvider의 getAuthentication 메서드로 Authentication 객체 생성
            Authentication authentication  = jwtProvider.getAuthentication(token);
            // SecurityContextHolder에 Authentication 객체를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 Bearer 를 제거하고 순수 토큰 값을 추출하는 메서드
    private String resolveToken(String authorization){
        // 주어진 authorization이 null이 아니고 Bearer로 시작한다면
        if (authorization != null && authorization.startsWith("Bearer ")) {
            // Bearer 를 제거하고 순수 토큰 값을 저장
            authorization = authorization.substring(7);
        } else {
            authorization = null; // 조건에 만족하지 않는다면 null
        }

        // 변환된 authorization 반환
        return authorization;
    }
}

package com.example.domain.auth.controller;

import com.example.domain.auth.dto.request.UserJoinRequest;
import com.example.domain.auth.dto.request.UserLoginRequest;
import com.example.domain.auth.dto.response.TokenReissueDTO;
import com.example.domain.auth.dto.response.UserLoginResponse;
import com.example.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    // 회원가입
    // /join 경로로 매핑
    @PostMapping("/join")
    // 반환 타입 - ResponseEntity<Void>
    // body에 UserJoinRequest(email, password) 값을 받음.
    // Valid로 RequestBody 객체 검증
    public ResponseEntity<Void> join(@RequestBody @Valid UserJoinRequest request) {
        // 주입받은 authService의 join 메서드에 UserJoinRequest에 받은 값을 넘겨줌
        authService.join(request);
        // ResponseEntity 리턴
        return ResponseEntity.ok().build();
    }

    // 로그인
    // /login 경로로 매핑
    @PostMapping("/login")
    // 반환 타입 - ResponseEntity<UserLoginResponse>
    // body로 UserLoginRequest(email, password) 값을 받음.
    // @Valid를 사용하여 RequestBody 객체 검증
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        // ResponseEntity.ok()를 사용하여 상태 코드 200과 함께
        // body에 authService의 login 메서드 호출 결과를 포함하여 반환
        return ResponseEntity.ok(authService.login(request));
    }

    // 토큰 재발급
    // /refresh 경로로 매핑
    @PostMapping("/refresh")
    // 반환 타입 - ResponseEntity<TokenReissueDTO>
    // HttpServletRequest로 요청을 받음
    public ResponseEntity<TokenReissueDTO> refresh(HttpServletRequest request) {
        // HttpServletRequest로부터 "Access-Token" 헤더 값을 가져옴
        String accessToken = request.getHeader("Access-Token");
        // HttpServletRequest로부터 "Refresh-Token" 헤더 값을 가져옴
        String refreshToken = request.getHeader("Refresh-Token");

        // authService의 refreshToken 메서드를 호출하여 새로운 토큰을 발급받고,
        // 그 결과를 포함하여 상태 코드 200과 함께 ResponseEntity로 반환
        return ResponseEntity.ok(authService.refreshToken(accessToken, refreshToken));
    }
}

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

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Valid UserJoinRequest request) {
        authService.join(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenReissueDTO> refresh(HttpServletRequest request) {
        String accessToken = request.getHeader("Access-Token");
        String refreshToken = request.getHeader("Refresh-Token");

        return ResponseEntity.ok(authService.refreshToken(accessToken, refreshToken));
    }
}

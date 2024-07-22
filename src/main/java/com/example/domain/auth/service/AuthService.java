package com.example.domain.auth.service;

import com.example.domain.auth.dto.request.UserJoinRequest;
import com.example.domain.auth.dto.request.UserLoginRequest;
import com.example.domain.auth.dto.response.TokenReissueDTO;
import com.example.domain.auth.dto.response.UserLoginResponse;
import com.example.domain.user.entity.UserEntity;
import com.example.domain.user.enums.Role;
import com.example.domain.user.enums.TokenType;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.ExpectedException;
import com.example.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jwt.refreshExp}")
    public Long refreshExp;

    public void join(UserJoinRequest joinRequest) {
        if (userRepository.existsByEmail(joinRequest.getEmail())) {
            throw new ExpectedException("이미 이 이메일을 사용하는 유저가 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        UserEntity user = UserEntity.builder()
                .id(null)
                .email(joinRequest.getEmail())
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ExpectedException("해당 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ExpectedException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return jwtProvider.generateTokenSet(user.getId());
    }

    public TokenReissueDTO refreshToken(String accessToken, String refreshToken) {
        accessToken = accessToken.substring(7);
        refreshToken = refreshToken.substring(7);

        Boolean validateAccess = jwtProvider.validateToken(accessToken);
        Boolean validateRefresh = jwtProvider.validateToken(refreshToken);

        UserEntity user = userRepository.findById(
                Long.valueOf(jwtProvider.getClaims(accessToken).getSubject())
        ).orElseThrow(() -> new ExpectedException("해당하는 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (validateAccess && validateRefresh) {
            throw new ExpectedException("엑세스 토큰과 리프레스 토큰이 모두 만료되었습니다.", HttpStatus.BAD_REQUEST);
        } else if (validateAccess) {
            return new TokenReissueDTO(
                    jwtProvider.generateToken(user.getId(), TokenType.ACCESS_TOKEN),
                    refreshToken
            );
        } else if (validateRefresh) {
            return new TokenReissueDTO(
                    accessToken,
                    jwtProvider.generateToken(user.getId(), TokenType.REFRESH_TOKEN)
            );
        } else {
            throw new ExpectedException("만료된 토큰이 없습니다", HttpStatus.BAD_REQUEST);
        }
    }
}

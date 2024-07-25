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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jwt.refreshExp}")
    public Long refreshExp;

    @Transactional

    /**
     * 회원가입 비즈니스 로직
     *
     * @param 회원가입 시 필요한 email과 password 정보를 담은 joinRequest
     */
    public void join(UserJoinRequest joinRequest) {
        // 만약 DB에 joinRequest에서 받은 이메일이 중복된다면
        if (userRepository.existsByEmail(joinRequest.getEmail())) {
            // ExpectedException을 던짐
            throw new ExpectedException("이미 이 이메일을 사용하는 유저가 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        // PasswordEncoder로부터 joinRequest에서 받은 Password를 암호화
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        // UserEntity 빌더 객체 생성
        UserEntity user = UserEntity.builder()
                .email(joinRequest.getEmail()) // email - joinRequest에서 받은 email을 가져옴
                .password(encodedPassword) // 암호화한 Password 가져옴
                .role(Role.ROLE_USER) // role에 User
                .build();

        userRepository.save(user); // User 객체 저장
    }

    // 로그인 비즈니스 로직
    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        // UserEntity 객체에 UserLoginRequeset DTO에서 받은 email로 유저를 찾음
        UserEntity user = userRepository.findByEmail(request.getEmail())
                // 찾지 못했따면 예외 던지기
                .orElseThrow(() -> new ExpectedException("해당 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 만약 UserLoginRequest에서 받은 비밀번호와 UserEntity 객체에서 가져온 비밀번호가 같지 않다면
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 비밀번호가 일치하지 않는다는 예외 던지기
            throw new ExpectedException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // if문을 통과하면 jwtProvider의 generatetokenSet 메서드 실행
        return jwtProvider.generateTokenSet(user.getId());
    }

    // 토큰 재발급 비즈니스 로직
    @Transactional
    public TokenReissueDTO refreshToken(String accessToken, String refreshToken) {
        // accessToken의 토큰의 접두사를 뺀 토큰 값 추출
        accessToken = accessToken.substring(7);
        // refreshToken의 토큰의 접두사를 빼 토큰 값 추출
        refreshToken = refreshToken.substring(7);

        // jwtProvider의 validateToken 메서드로 accessToken 검증
        Boolean validateAccess = jwtProvider.validateToken(accessToken);
        // jwtProvider의 validateToken 메서드로 refreshToken 검증
        Boolean validateRefresh = jwtProvider.validateToken(refreshToken);

        // UserEntity 객체를 생성해서 accessToken의 subject를 추출해 유저를 찾아 저장
        UserEntity user = userRepository.findById(UUID.fromString(jwtProvider.getClaims(accessToken).getSubject()))
                // 찾지 못했다면 유저를 찾을 수 없다는 예외 던지기
                .orElseThrow(() -> new ExpectedException("해당하는 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 만약 validateAcces와 validateRefresh가 둘 다 true일 때
        if (validateAccess && validateRefresh) {
            // 두 토큰이 다 만료되었다는 예외 더지기
            throw new ExpectedException("엑세스 토큰과 리프레스 토큰이 모두 만료되었습니다.", HttpStatus.BAD_REQUEST);
        // 만약 validateAccess가 true라면
        } else if (validateAccess) {
            // 새로운 TokenReissueDTO 객체를 만들어 accessToken을 재발급
            return new TokenReissueDTO(
                    jwtProvider.generateToken(user.getId(), TokenType.ACCESS_TOKEN),
                    refreshToken
            );
        // 만약 validateRefresh가 true라면
        } else if (validateRefresh) {
            // 새로운 TokenReissueDTO 객체를 만들어 refreshToken을 재발급
            return new TokenReissueDTO(
                    accessToken,
                    jwtProvider.generateToken(user.getId(), TokenType.REFRESH_TOKEN)
            );
        // 모두 아닐 경우
        } else {
            // 만료된 토큰이 없다는 예외 던지기
            throw new ExpectedException("만료된 토큰이 없습니다", HttpStatus.BAD_REQUEST);
        }
    }
}

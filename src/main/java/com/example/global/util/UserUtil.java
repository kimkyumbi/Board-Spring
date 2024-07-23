package com.example.global.util;

import com.example.domain.user.entity.UserEntity;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    // UserEntity에서 user를 찾는 메서드
    public UserEntity getCurrentUser() {
        // userRepository의 findByEmail 메서드를 SecurityContextHolder에서 Context를 가져오고 Context에서 Authentication을 가져와 name을 가져온다 (name = email)
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                // 유저를 찾지 못하면 유저를 찾을 수 없다는 예외 던지기
                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}

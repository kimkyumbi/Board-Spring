package com.example.global.util;

import com.example.domain.user.entity.UserEntity;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Configuration
@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}

package com.example.global.security.principle;

import com.example.domain.user.entity.UserEntity;
import com.example.domain.user.repository.UserRepository;
import com.example.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override // UserDetailsService 구현체로서 loadUserByUsername 메서드 오버라이드
    // 파라미터로 받은 userId로 UserDetails 객체를 생성하는 메서드
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // UserEntity 객체를 생성하고 userRepository에서 findByUserId 메서드로 String으로 받은 userId를 Long으로 형변환해 객체에 저장
        UserEntity user = userRepository.findById(UUID.fromString(id))
                // 유저를 찾지 못했을 때 예외 처리
                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        // UserDetails 구현체인 AuthDetails 객체 생성
        return new AuthDetails(user);
    }
}

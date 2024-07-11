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

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserId(Long.valueOf(userId))
                .orElseThrow(() -> new ExpectedException("유저를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        return new AuthDetails(user);
    }
}

package com.example.global.config;

import com.example.global.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JwtFilter를 주입받음
    private final JwtFilter jwtFilter;

    // Spring Bean으로 등록
    @Bean
    // Security 설정을 위한 메서드
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 보호를 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 세션 관리 정책을 STATELESS로 설정해 서버가 세션을 생성하지 않도록 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // CORS 설정을 따로 만들어둔 corsConfigurationSource 메서드를 호출해 설정
        http.cors((cors) ->
                cors.configurationSource(corsConfigurationSource())
        );

        // 각 요청에 대한 권한 설정
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers("/**").permitAll()
        );

        // UsernamePasswordAuthenticationFilter 앞에 커스텀 필터인 JwtFilter 추가
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // 설정을 기반으로 SecurityFilterChain 객체 생성
        return http.build();
    }

    // Spring Bean으로 등록
    @Bean
    // Cors설정 메서드
    public CorsConfigurationSource corsConfigurationSource() {
        // CorsConfigration 객체 생성
        CorsConfiguration configuration = new CorsConfiguration();

        // 자격 증멸 포함 여부 설정
        configuration.setAllowCredentials(true);
        // 접근을 허용할 Origin 설정 / http://localhost:8083과 http://localhost:8080에서의 접근 허용
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8083", "http://localhost:8080", "http://localhost:3000", "http://localhost:3001"));
        // 접근을 허용할 HTTP 메서드 설정 / GET, POST, PUT, DELETE, OPTIONS 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        // Pre-flight 요청의 캐시 지속 시간을 설정 / 6400초 동안 캐시
        configuration.setMaxAge(6400L);

        // UrlBasedCorsConfiguationSource 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해서 위에서 적용한 Cors 정책 적용
        source.registerCorsConfiguration("/**", configuration);

        // 경로 설정이 적용된 UrlBasedCorsConfiguationSource 객체 반환
        return source;
    }

    // PasswordEncoder를 스프링 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

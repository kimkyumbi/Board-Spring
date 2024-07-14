package com.example.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expired_at")
    private Long expiredAt;

    public ReissueTokenEntity(String refreshToken, Long expiredAt) {
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }
}

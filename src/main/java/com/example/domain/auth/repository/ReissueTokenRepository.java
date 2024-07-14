package com.example.domain.auth.repository;

import com.example.domain.auth.entity.ReissueTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReissueTokenRepository extends CrudRepository<ReissueTokenEntity, Long> {
    Optional<ReissueTokenEntity> findByUserId(Long userId);
}

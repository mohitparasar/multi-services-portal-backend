package com.msp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.entity.RefreshToken;
import com.msp.entity.UserEntity;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(UserEntity user);
	void deleteByUser(UserEntity user);

}
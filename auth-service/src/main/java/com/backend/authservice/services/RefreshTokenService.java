package com.backend.authservice.services;


import com.backend.authservice.entities.RefreshToken;
import com.backend.authservice.entities.Users;
import com.backend.authservice.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh-token-expiration:604800000}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken createRefreshToken(Users user) {
        refreshTokenRepository.revokeAllUserTokens(user.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration))
                .isRevoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByTokenAndIsRevokedFalse(token);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.isExpired()){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.findByTokenAndIsRevokedFalse(token)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });
    }

    @Transactional
    public void revokeAllUserTokens(String userId){
        refreshTokenRepository.revokeAllUserTokens(userId);
    }
}

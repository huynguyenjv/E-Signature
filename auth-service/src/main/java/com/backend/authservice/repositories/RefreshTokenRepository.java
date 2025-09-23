package com.backend.authservice.repositories;

import com.backend.authservice.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByTokenAndIsRevokedFalse(String token);

    @Modifying
    @Query("UPDATE refresh_tokens rt SET rt.isRevoked = true WHERE rt.user.id = :userId")
    void revokeAllUserTokens(String userId);

    void deleteByUser_Id(String userId);
}

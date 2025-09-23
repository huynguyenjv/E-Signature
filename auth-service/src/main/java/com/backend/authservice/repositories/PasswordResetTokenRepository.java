package com.backend.authservice.repositories;


import com.backend.authservice.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,String> {
    Optional<PasswordResetToken> findByTokenAndIsUsedFalse(String token);
    void deleteByUser_Id(String userId);
}

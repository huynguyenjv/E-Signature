package com.backend.esignature.config.security;

import com.backend.esignature.services.persistence.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret = "myVeryLongSecretKeyForJWTTokenThatIsAtLeast256BitsLongAndSecure";
    private long accessTokenExpiration = 900000; // 15 minutes
    private long refreshTokenExpiration = 604800000; // 7 days
    private String tokenPrefix = "Bearer ";
    private String headerString = "Authorization";
}

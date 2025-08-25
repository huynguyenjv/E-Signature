package com.backend.esignature.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Login response with JWT tokens and user info")
public class LoginResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "JWT refresh token")
    private String refreshToken;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "Token expiration time in seconds", example = "900")
    private Long expiresIn;

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Email", example = "admin@example.com")
    private String email;

    @Schema(description = "Full name", example = "Administrator")
    private String fullName;

    @Schema(description = "User roles", example = "[\"ADMIN\", \"USER\"]")
    private List<String> roles;
}

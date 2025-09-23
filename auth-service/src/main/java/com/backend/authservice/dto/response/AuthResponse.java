package com.backend.authservice.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserInfo userInfo;

    @Data
    public static class UserInfo {
        private String id;
        private String username;
        private String email;
        private String fullName;
        private List<String> roles;
        private boolean isActive;
    }
}

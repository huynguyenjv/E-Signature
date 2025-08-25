package com.backend.esignature.dto.requests;

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
        private String avatarUrl;
        private String subscriptionType;
        private List<String> roles;
        private boolean isActive;
    }
}

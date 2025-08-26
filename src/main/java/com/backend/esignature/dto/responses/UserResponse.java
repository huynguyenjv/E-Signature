package com.backend.esignature.dto.responses;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String avatar_url;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp lastLogin;
    private boolean isActive;
    private RoleResponse roles;
}

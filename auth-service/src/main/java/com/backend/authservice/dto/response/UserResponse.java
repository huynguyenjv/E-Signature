package com.backend.authservice.dto.response;


import lombok.*;

import java.sql.Timestamp;
import java.util.List;

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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp lastLogin;
    private boolean isActive;
    private List<RoleResponse> roles;
}

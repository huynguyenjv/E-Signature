package com.backend.esignature.dto.responses;


import lombok.*;

import java.sql.Timestamp;
import java.util.List;

import com.backend.esignature.dto.responses.RoleResponse;

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
    private List<RoleResponse> roles;
}

package com.backend.userservice.dto.response;

import com.backend.userservice.enums.UserRole;
import com.backend.userservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private UserStatus status;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;

}

package com.backend.authservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    String id;
    String username;
    String email;
    String password;
    String fullName;
    String avatarUrl;
}

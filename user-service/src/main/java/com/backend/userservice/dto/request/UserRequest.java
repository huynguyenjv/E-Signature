package com.backend.userservice.dto.request;

import com.backend.userservice.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String email;
    private String fullName;
    private String phoneName;
    private UserRole role;
}

package com.backend.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    String id;
    String name;
    String description;
    Timestamp createdAt;
    Timestamp updatedAt;
}

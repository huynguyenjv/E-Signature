package com.backend.esignature.dto.responses;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogResponse {
    private String id;
    private String userId;
    private String documentId;
    private String action;
    private String details;
    private String ipAddress;
    private String userAgent;
    private Timestamp createdAt;
}

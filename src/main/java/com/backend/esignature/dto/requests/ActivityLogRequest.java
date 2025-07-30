package com.backend.esignature.dto.requests;

import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogRequest {
    private String id;
    private String userId;
    private String documentId;
    private String action;
    private String details;
    private String ipAddress;
    private String userAgent;
    private Timestamp createdAt;
}

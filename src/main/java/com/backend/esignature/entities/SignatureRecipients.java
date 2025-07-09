package com.backend.esignature.entities;

import com.backend.esignature.enums.RoleEnum;
import com.backend.esignature.enums.StatusEnum;
import com.backend.esignature.enums.VerificationMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "signature_recipients", indexes = {
        @Index(name = "idx_signature_recipients_request_id", columnList = "request_id"),
        @Index(name = "idx_signature_recipients_email", columnList = "recipient_email"),
        @Index(name = "idx_signature_recipients_token", columnList = "access_token"),
        @Index(name = "idx_signature_recipients_status", columnList = "status")
})
public class SignatureRecipients {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false, foreignKey = @ForeignKey(name = "fk_signature_recipients_request_id"))
    private SignatureRequests request;

    @Column(name = "recipient_email", nullable = false, length = 100)
    private String recipientEmail;

    @Column(name = "recipient_name", length = 100)
    private String recipientName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_user_id", foreignKey = @ForeignKey(name = "fk_signature_recipients_recipient_user_id"))
    private Users recipientUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role = RoleEnum.signer;

    @Column(name = "signing_order", nullable = false)
    private Integer signingOrder = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status = StatusEnum.pending;

    @Column(name = "access_token", nullable = false, unique = true, length = 100)
    private String accessToken;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_method", nullable = false)
    private VerificationMethodEnum verificationMethod = VerificationMethodEnum.email;

    @Column(name = "verification_code", length = 10)
    private String verificationCode;

    @Column(name = "verification_expires_at")
    private Timestamp verificationExpiresAt;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @Column(name = "personal_message", columnDefinition = "TEXT")
    private String personalMessage;

    @Column(name = "viewed_at")
    private Timestamp viewedAt;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    @Column(name = "declined_at")
    private Timestamp declinedAt;

    @Column(name = "decline_reason", columnDefinition = "TEXT")
    private String declineReason;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssignedFields> assignedFields;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (role == null) {
            role = RoleEnum.signer;
        }
        if (signingOrder == null) {
            signingOrder = 1;
        }
        if (status == null) {
            status = StatusEnum.pending;
        }
        if (verificationMethod == null) {
            verificationMethod = VerificationMethodEnum.email;
        }
        if (isVerified == true) {
            isVerified = false;
        }
    }

}
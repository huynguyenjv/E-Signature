package com.backend.esignature.entities;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "signature_audit_trail", indexes = {
        @Index(name = "idx_signature_audit_trail_request_id", columnList = "request_id"),
        @Index(name = "idx_signature_audit_trail_created_at", columnList = "created_at DESC")
})
public class SignatureAuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false, foreignKey = @ForeignKey(name = "fk_signature_audit_trail_request_id"))
    private SignatureRequests request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", foreignKey = @ForeignKey(name = "fk_signature_audit_trail_recipient_id"))
    private SignatureRecipients recipient;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "actor_email", length = 100)
    private String actorEmail;

    @Column(name = "actor_ip", length = 45)
    private String actorIp;

    @Column(name = "actor_user_agent", columnDefinition = "TEXT")
    private String actorUserAgent;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}

package com.backend.esignature.entities;

import com.backend.esignature.enums.SignatureTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "digital_signatures", indexes = {
        @Index(name = "idx_digital_signatures_request_id", columnList = "request_id"),
        @Index(name = "idx_digital_signatures_recipient_id", columnList = "recipient_id")
})
public class DigitalSignatures {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false, foreignKey = @ForeignKey(name = "fk_digital_signatures_request_id"))
    private SignatureRequests request;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_digital_signatures_recipient_id"))
    private SignatureRecipients recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_field_id", foreignKey = @ForeignKey(name = "fk_digital_signatures_assigned_field_id"))
    private AssignedFields assignedField;

    @Enumerated(EnumType.STRING)
    @Column(name = "signature_type", nullable = false)
    private SignatureTypeEnum signatureType;

    @Column(name = "signature_data", nullable = false, columnDefinition = "LONGTEXT")
    private String signatureData;

    @Column(name = "signature_image_url", columnDefinition = "TEXT")
    private String signatureImageUrl;

    @Column(name = "certificate_info", columnDefinition = "JSON")
    private String certificateInfo;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(columnDefinition = "JSON")
    private String geolocation;

    @Column(name = "timestamp_server", length = 100)
    private String timestampServer;

    @Column(name = "hash_algorithm", length = 20)
    private String hashAlgorithm = "SHA-256";

    @Column(name = "signature_hash", length = 128)
    private String signatureHash;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (hashAlgorithm == null) {
            hashAlgorithm = "SHA-256";
        }
        if (isValid == false) {
            isValid = true;
        }
    }
}
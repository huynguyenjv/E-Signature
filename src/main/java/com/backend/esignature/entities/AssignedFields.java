package com.backend.esignature.entities;

import com.backend.esignature.enums.FieldTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assigned_fields", indexes = {
        @Index(name = "idx_assigned_fields_request_id", columnList = "request_id"),
        @Index(name = "idx_assigned_fields_recipient_id", columnList = "recipient_id"),
        @Index(name = "idx_assigned_fields_form_field_id", columnList = "form_field_id")
})
public class AssignedFields {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assigned_fields_request_id"))
    private SignatureRequests request;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assigned_fields_recipient_id"))
    private SignatureRecipients recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_field_id", foreignKey = @ForeignKey(name = "fk_assigned_fields_form_field_id"))
    private FormFields formField;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    private FieldTypeEnum fieldType;

    @Column(name = "field_label", length = 100)
    private String fieldLabel;

    @Column(name = "position_x", nullable = false)
    private Float positionX;

    @Column(name = "position_y", nullable = false)
    private Float positionY;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired = true;

    @Column(name = "placeholder_text", length = 200)
    private String placeholderText;

    @Column(name = "validation_rules", columnDefinition = "JSON")
    private String validationRules;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    @Column(name = "field_value", columnDefinition = "TEXT")
    private String fieldValue;

    @Column(name = "signature_image_url", columnDefinition = "TEXT")
    private String signatureImageUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (isRequired == false) {
            isRequired = true;
        }
    }
}
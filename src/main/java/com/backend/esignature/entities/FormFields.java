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
@Table(name = "form_fields", indexes = {
        @Index(name = "idx_form_fields_document_id", columnList = "document_id")
})
public class FormFields {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "fk_form_fields_document_id"))
    private Documents document;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "page_id", nullable = false, foreignKey = @ForeignKey(name = "fk_form_fields_page_id"))
    private DocumentPages page;

    @Column(name = "field_name", nullable = false, length = 100)
    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    private FieldTypeEnum fieldType;

    @Column(name = "position_x", nullable = false)
    private Float positionX;

    @Column(name = "position_y", nullable = false)
    private Float positionY;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(name = "default_value", columnDefinition = "TEXT")
    private String defaultValue;

    @Column(columnDefinition = "JSON")
    private String options;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired = false;

    @Column(name = "is_readonly", nullable = false)
    private boolean isReadonly = false;

    @Column(name = "validation_rules", columnDefinition = "JSON")
    private String validationRules;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (isRequired == true) {
            isRequired = false;
        }
        if (isReadonly == true) {
            isReadonly = false;
        }
    }
}
package com.backend.esignature.entities;

import com.backend.esignature.enums.RequestTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request_templates")
public class RequestTemplates {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_request_templates_user_id"))
    private Users user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "title_template", length = 200)
    private String titleTemplate;

    @Column(name = "message_template", columnDefinition = "TEXT")
    private String messageTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    private RequestTypeEnum requestType;

    @Column(name = "due_days", nullable = false)
    private Integer dueDays = 7;

    @Column(name = "reminder_frequency", nullable = false)
    private Integer reminderFrequency = 3;

    @Column(name = "assigned_fields", columnDefinition = "JSON")
    private String assignedFields;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @Column(name = "usage_count", nullable = false)
    private Integer usageCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
        if (dueDays == null) {
            dueDays = 7;
        }
        if (reminderFrequency == null) {
            reminderFrequency = 3;
        }
        if (isPublic == true) {
            isPublic = false;
        }
        if (usageCount == null) {
            usageCount = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
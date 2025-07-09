package com.backend.esignature.entities;

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
@Table(name = "signature_requests", indexes = {
        @Index(name = "idx_signature_requests_document_id", columnList = "document_id"),
        @Index(name = "idx_signature_requests_created_by", columnList = "created_by"),
        @Index(name = "idx_signature_requests_status", columnList = "status"),
        @Index(name = "idx_signature_requests_due_date", columnList = "due_date")
})
public class SignatureRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "fk_signature_requests_document_id"))
    private Documents document;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, foreignKey = @ForeignKey(name = "fk_signature_requests_created_by"))
    private Users createdBy;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.draft;

    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "reminder_frequency", nullable = false)
    private Integer reminderFrequency = 3;

    @Column(name = "last_reminder_sent")
    private Timestamp lastReminderSent;

    @Enumerated(EnumType.STRING)
    @Column(name = "completion_order", nullable = false)
    private CompletionOrder completionOrder = CompletionOrder.any;

    @Column(name = "require_all_recipients", nullable = false)
    private boolean requireAllRecipients = true;

    @Column(name = "allow_decline", nullable = false)
    private boolean allowDecline = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "sent_at")
    private Timestamp sentAt;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SignatureRecipients> recipients;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = Status.draft;
        }
        if (reminderFrequency == null) {
            reminderFrequency = 3;
        }
        if (completionOrder == null) {
            completionOrder = CompletionOrder.any;
        }
        if (requireAllRecipients == false) {
            requireAllRecipients = true;
        }
        if (allowDecline == false) {
            allowDecline = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public enum RequestType {
        signature, form_fill, both
    }

    public enum Status {
        draft, sent, in_progress, completed, cancelled, expired
    }

    public enum CompletionOrder {
        any, sequential
    }
}

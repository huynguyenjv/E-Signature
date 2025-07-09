package com.backend.esignature.entities;

import com.backend.esignature.enums.DeliveryMethodEnum;
import com.backend.esignature.enums.NotificationTypeEnum;
import com.backend.esignature.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request_notifications", indexes = {
        @Index(name = "idx_request_notifications_request_id", columnList = "request_id"),
        @Index(name = "idx_request_notifications_status", columnList = "status"),
        @Index(name = "idx_request_notifications_sent_at", columnList = "sent_at")
})
public class RequestNotifications {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false, foreignKey = @ForeignKey(name = "fk_request_notifications_request_id"))
    private SignatureRequests request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", foreignKey = @ForeignKey(name = "fk_request_notifications_recipient_id"))
    private SignatureRecipients recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationTypeEnum notificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method", nullable = false)
    private DeliveryMethodEnum deliveryMethod;

    @Column(name = "recipient_contact", nullable = false, length = 100)
    private String recipientContact;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status = StatusEnum.pending;

    @Column(name = "sent_at")
    private Timestamp sentAt;

    @Column(name = "delivered_at")
    private Timestamp deliveredAt;

    @Column(name = "opened_at")
    private Timestamp openedAt;

    @Column(name = "clicked_at")
    private Timestamp clickedAt;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (status == null) {
            status = StatusEnum.pending;
        }
        if (retryCount == null) {
            retryCount = 0;
        }
    }

}
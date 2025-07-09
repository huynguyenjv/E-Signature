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
@Table(name = "form_submissions")
public class FormSubmissions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "fk_form_submissions_document_id"))
    private Documents document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by", foreignKey = @ForeignKey(name = "fk_form_submissions_submitted_by"))
    private Users submittedBy;

    @Column(name = "submitter_email", length = 100)
    private String submitterEmail;

    @Column(name = "form_data", nullable = false, columnDefinition = "JSON")
    private String formData;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}

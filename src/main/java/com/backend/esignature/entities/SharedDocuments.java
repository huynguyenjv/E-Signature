package com.backend.esignature.entities;

import com.backend.esignature.enums.PermissionLevelEnum;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shared_documents", indexes = {
        @Index(name = "idx_shared_documents_document_id", columnList = "document_id"),
        @Index(name = "idx_shared_documents_token", columnList = "share_token")
})
public class SharedDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "fk_shared_documents_document_id"))
    private Documents document;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shared_by", nullable = false, foreignKey = @ForeignKey(name = "fk_shared_documents_shared_by"))
    private Users sharedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with", foreignKey = @ForeignKey(name = "fk_shared_documents_shared_with"))
    private Users sharedWith;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_level", nullable = false)
    private PermissionLevelEnum permissionLevel;

    @Column(name = "share_token", length = 100, unique = true)
    private String shareToken;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        if (isActive == false) {
            isActive = true;
        }
    }
}
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
@Table(name = "document_folders")
public class DocumentFolders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", foreignKey = @ForeignKey(name = "fk_document_folders_document_id"))
    private Documents document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", foreignKey = @ForeignKey(name = "fk_document_folders_folder_id"))
    private Folders folder;

    @Column(name = "added_at", nullable = false, updatable = false)
    private Timestamp addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = new Timestamp(System.currentTimeMillis());
    }
}
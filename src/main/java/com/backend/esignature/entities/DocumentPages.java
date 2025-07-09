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
@Table(name = "document_pages", indexes = {
        @Index(name = "idx_documents_pages_document_id", columnList = "document_id")
})
public class DocumentPages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", unique = true, nullable = false, foreignKey = @ForeignKey(name = "document_page_document_id"))
    private Documents document;

    @Column(name = "page_number", nullable = false, unique = true)
    private Integer pageNumber;

    @Column(name = "thumbnail_url", columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(name = "width", nullable = false, columnDefinition = "FLOAT")
    private Float width;

    @Column(name = "height", nullable = false, columnDefinition = "FLOAT")
    private Float height;

    @Column(name = "content_hash")
    private String contentHash;

    @Column(name = "annotations_count")
    private Integer annotationsCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        annotationsCount = 0;
    }


}

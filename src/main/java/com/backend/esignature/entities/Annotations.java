package com.backend.esignature.entities;

import com.backend.esignature.enums.AnnotationTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "annotations", indexes = {
        @Index(name = "idx_annotations_document_id", columnList = "document_id"),
        @Index(name = "idx_annotations_page_id", columnList = "page_id"),
        @Index(name = "idx_annotations_user_id", columnList = "user_id")
})
public class Annotations {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "fk_annotations_document_id"))
    private Documents document;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "page_id", nullable = false, foreignKey = @ForeignKey(name = "fk_annotations_page_id"))
    private DocumentPages pages;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_annnotations_user_id"))
    private Users user;

    @Column(name = "annotation_type")
    private AnnotationTypeEnum annotationType;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "position_x", columnDefinition = "FLOAT")
    private Float positionX;

    @Column(name = "positon_y", columnDefinition = "FLOAT")
    private Float positionY;

    @Column(name ="width", columnDefinition = "FLOAT")
    private Float width;

    @Column(name = "height", columnDefinition = "FLOAT")
    private Float height;

    @Column(name = "color")
    private String color;

    @Column(name = "font_size")
    private Integer fontSize;

    @Column(name = "font_family")
    private String fontFamily;

    @Column(name = "opacity", columnDefinition = "FLOAT")
    private Float opacity;

    @Column(name = "rotation", columnDefinition = "FLOAT")
    private Float rotation;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
        color = "#000000";
        fontSize = 12;
        fontFamily = "Arial";
        opacity = 1.0f;
        rotation = 0f;
        isLocked = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}

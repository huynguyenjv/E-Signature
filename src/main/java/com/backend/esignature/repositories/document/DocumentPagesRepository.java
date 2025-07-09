package com.backend.esignature.repositories.document;

import com.backend.esignature.entities.DocumentPages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentPagesRepository extends JpaRepository<DocumentPages, String> {
}

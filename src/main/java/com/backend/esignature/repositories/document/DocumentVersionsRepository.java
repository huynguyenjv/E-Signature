package com.backend.esignature.repositories.document;

import com.backend.esignature.entities.DocumentVersions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentVersionsRepository extends JpaRepository<DocumentVersions, String> {
}

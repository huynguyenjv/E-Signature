package com.backend.esignature.repositories.document;

import com.backend.esignature.entities.DocumentFolders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFoldersRepository extends JpaRepository<DocumentFolders, String> {
}

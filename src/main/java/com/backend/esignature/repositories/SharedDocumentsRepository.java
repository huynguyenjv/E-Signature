package com.backend.esignature.repositories;

import com.backend.esignature.entities.SharedDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedDocumentsRepository extends JpaRepository<SharedDocuments, String> {
}

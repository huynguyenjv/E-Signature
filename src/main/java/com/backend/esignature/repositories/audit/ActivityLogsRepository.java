package com.backend.esignature.repositories.audit;

import com.backend.esignature.entities.ActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, String> {
    void deleteByDocumentId(String documentId);
    void deleteByUserId(String userId);
    List<ActivityLogs> findByUserId(String userId);
    List<ActivityLogs> findByDocumentId(String documentId);
}

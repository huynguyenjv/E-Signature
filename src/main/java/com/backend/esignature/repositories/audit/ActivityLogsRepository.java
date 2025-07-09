package com.backend.esignature.repositories.audit;

import com.backend.esignature.entities.ActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, String> {

}

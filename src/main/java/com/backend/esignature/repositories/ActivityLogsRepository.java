package com.backend.esignature.repositories;

import com.backend.esignature.entities.ActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, Long> {
}

package com.backend.esignature.repositories.notification;

import com.backend.esignature.entities.RequestNotifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestNotificationsRepository extends JpaRepository<RequestNotifications, String> {
}

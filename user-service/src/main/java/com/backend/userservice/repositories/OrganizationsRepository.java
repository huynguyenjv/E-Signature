package com.backend.userservice.repositories;

import com.backend.userservice.entities.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationsRepository extends JpaRepository<Organizations, String> {
}

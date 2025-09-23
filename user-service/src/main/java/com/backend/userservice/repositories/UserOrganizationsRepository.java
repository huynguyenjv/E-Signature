package com.backend.userservice.repositories;

import com.backend.userservice.entities.UserOrganizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrganizationsRepository extends JpaRepository<UserOrganizations, String> {
}

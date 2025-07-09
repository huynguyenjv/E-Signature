package com.backend.esignature.repositories.templates;

import com.backend.esignature.entities.RequestTemplates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTemplatesRepository extends JpaRepository<RequestTemplates, String> {
}

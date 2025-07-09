package com.backend.esignature.repositories.form;

import com.backend.esignature.entities.FormSubmissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmissions,String> {
}

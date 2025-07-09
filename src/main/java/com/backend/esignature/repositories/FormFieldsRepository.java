package com.backend.esignature.repositories;

import com.backend.esignature.entities.FormFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldsRepository extends JpaRepository<FormFields, String> {
}

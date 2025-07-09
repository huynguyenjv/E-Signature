package com.backend.esignature.repositories;

import com.backend.esignature.entities.Annotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationsRepository extends JpaRepository<Annotations, String> {
}

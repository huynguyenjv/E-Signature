package com.backend.esignature.repositories;


import com.backend.esignature.entities.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, String> {
}

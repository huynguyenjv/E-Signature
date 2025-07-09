package com.backend.esignature.repositories;

import com.backend.esignature.entities.DigitalSignatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalSignaturesRepository extends JpaRepository<DigitalSignatures, String> {
}

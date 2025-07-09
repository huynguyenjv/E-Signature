package com.backend.esignature.repositories.signing;

import com.backend.esignature.entities.SignatureAuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureAuditTrailRepository extends JpaRepository<SignatureAuditTrail, String> {
}

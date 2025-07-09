package com.backend.esignature.repositories.signing;

import com.backend.esignature.entities.SignatureRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRequestsRepository extends JpaRepository<SignatureRequests, String> {
}

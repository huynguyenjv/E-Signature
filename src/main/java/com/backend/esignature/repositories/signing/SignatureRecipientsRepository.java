package com.backend.esignature.repositories.signing;

import com.backend.esignature.entities.SignatureRecipients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRecipientsRepository extends JpaRepository<SignatureRecipients, String> {
}

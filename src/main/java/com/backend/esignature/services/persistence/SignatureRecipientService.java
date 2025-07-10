package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.SignatureRecipientRequest;
import com.backend.esignature.dto.responses.SignatureRecipientResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureRecipientService implements ICRUDService<SignatureRecipientRequest, SignatureRecipientResponse,String> {
    @Override
    public SignatureRecipientResponse create(SignatureRecipientRequest entity) {
        return null;
    }

    @Override
    public SignatureRecipientResponse update(SignatureRecipientRequest entity, String id) {
        return null;
    }

    @Override
    public SignatureRecipientResponse delete(String id) {
        return null;
    }

    @Override
    public SignatureRecipientResponse findById(SignatureRecipientRequest entity) {
        return null;
    }

    @Override
    public List<SignatureRecipientResponse> findAll() {
        return List.of();
    }
}

package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.SignatureRequestRequest;
import com.backend.esignature.dto.responses.SignatureRequestResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureRequestService implements ICRUDService<SignatureRequestRequest, SignatureRequestResponse, String> {
    @Override
    public SignatureRequestResponse create(SignatureRequestRequest entity) {
        return null;
    }

    @Override
    public SignatureRequestResponse update(SignatureRequestRequest entity, String id) {
        return null;
    }

    @Override
    public SignatureRequestResponse delete(String id) {
        return null;
    }

    @Override
    public SignatureRequestResponse findById(SignatureRequestRequest entity) {
        return null;
    }

    @Override
    public List<SignatureRequestResponse> findAll() {
        return List.of();
    }
}

package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.DigitalSignatureRequest;
import com.backend.esignature.dto.responses.DigitalSignatureResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DigitalSignatureService implements ICRUDService<DigitalSignatureRequest, DigitalSignatureResponse,String> {
    @Override
    public DigitalSignatureResponse create(DigitalSignatureRequest entity) {
        return null;
    }

    @Override
    public DigitalSignatureResponse update(DigitalSignatureRequest entity, String id) {
        return null;
    }

    @Override
    public DigitalSignatureResponse delete(String id) {
        return null;
    }

    @Override
    public DigitalSignatureResponse findById(DigitalSignatureRequest entity) {
        return null;
    }

    @Override
    public List<DigitalSignatureResponse> findAll() {
        return List.of();
    }
}

package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.SignatureAuditTrailRequest;
import com.backend.esignature.dto.responses.SignatureAuditTrailResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignatureAuditTrailService implements ICRUDService<SignatureAuditTrailRequest, SignatureAuditTrailResponse,String> {
    @Override
    public SignatureAuditTrailResponse create(SignatureAuditTrailRequest entity) {
        return null;
    }

    @Override
    public SignatureAuditTrailResponse update(SignatureAuditTrailRequest entity, String id) {
        return null;
    }

    @Override
    public SignatureAuditTrailResponse delete(String id) {
        return null;
    }

    @Override
    public SignatureAuditTrailResponse findById(SignatureAuditTrailRequest entity) {
        return null;
    }

    @Override
    public List<SignatureAuditTrailResponse> findAll() {
        return List.of();
    }
}

package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.DocumentVersionRequest;
import com.backend.esignature.dto.responses.DocumentVersionResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentVersionService implements ICRUDService<DocumentVersionRequest, DocumentVersionResponse,String> {
    @Override
    public DocumentVersionResponse create(DocumentVersionRequest entity) {
        return null;
    }

    @Override
    public DocumentVersionResponse update(DocumentVersionRequest entity, String id) {
        return null;
    }

    @Override
    public DocumentVersionResponse delete(String id) {
        return null;
    }

    @Override
    public DocumentVersionResponse findById(DocumentVersionRequest entity) {
        return null;
    }

    @Override
    public List<DocumentVersionResponse> findAll() {
        return List.of();
    }
}

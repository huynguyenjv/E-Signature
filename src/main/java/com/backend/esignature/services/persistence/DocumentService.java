package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.DocumentRequest;
import com.backend.esignature.dto.responses.DocumentResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService implements ICRUDService<DocumentRequest, DocumentResponse,String> {
    @Override
    public DocumentResponse create(DocumentRequest entity) {
        return null;
    }

    @Override
    public DocumentResponse update(DocumentRequest entity, String id) {
        return null;
    }

    @Override
    public DocumentResponse delete(String id) {
        return null;
    }

    @Override
    public DocumentResponse findById(DocumentRequest entity) {
        return null;
    }

    @Override
    public List<DocumentResponse> findAll() {
        return List.of();
    }
}

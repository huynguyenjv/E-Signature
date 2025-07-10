package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.DocumentPageRequest;
import com.backend.esignature.dto.responses.DocumentPageResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentPageService implements ICRUDService<DocumentPageRequest, DocumentPageResponse,String> {
    @Override
    public DocumentPageResponse create(DocumentPageRequest entity) {
        return null;
    }

    @Override
    public DocumentPageResponse update(DocumentPageRequest entity, String id) {
        return null;
    }

    @Override
    public DocumentPageResponse delete(String id) {
        return null;
    }

    @Override
    public DocumentPageResponse findById(DocumentPageRequest entity) {
        return null;
    }

    @Override
    public List<DocumentPageResponse> findAll() {
        return List.of();
    }
}

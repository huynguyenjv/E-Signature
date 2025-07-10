package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.SharedDocumentRequest;
import com.backend.esignature.dto.responses.SharedDocumentResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedDocumentService implements ICRUDService<SharedDocumentRequest, SharedDocumentResponse, String> {
    @Override
    public SharedDocumentResponse create(SharedDocumentRequest entity) {
        return null;
    }

    @Override
    public SharedDocumentResponse update(SharedDocumentRequest entity, String id) {
        return null;
    }

    @Override
    public SharedDocumentResponse delete(String id) {
        return null;
    }

    @Override
    public SharedDocumentResponse findById(SharedDocumentRequest entity) {
        return null;
    }

    @Override
    public List<SharedDocumentResponse> findAll() {
        return List.of();
    }
}

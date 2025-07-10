package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.DocumentFolderRequest;
import com.backend.esignature.dto.responses.DocumentFolderResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentFolderService implements ICRUDService<DocumentFolderRequest, DocumentFolderResponse,String> {
    @Override
    public DocumentFolderResponse create(DocumentFolderRequest entity) {
        return null;
    }

    @Override
    public DocumentFolderResponse update(DocumentFolderRequest entity, String id) {
        return null;
    }

    @Override
    public DocumentFolderResponse delete(String id) {
        return null;
    }

    @Override
    public DocumentFolderResponse findById(DocumentFolderRequest entity) {
        return null;
    }

    @Override
    public List<DocumentFolderResponse> findAll() {
        return List.of();
    }
}

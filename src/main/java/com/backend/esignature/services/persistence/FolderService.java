package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.FolderRequest;
import com.backend.esignature.dto.responses.FolderResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService implements ICRUDService<FolderRequest, FolderResponse,String> {
    @Override
    public FolderResponse create(FolderRequest entity) {
        return null;
    }

    @Override
    public FolderResponse update(FolderRequest entity, String id) {
        return null;
    }

    @Override
    public FolderResponse delete(String id) {
        return null;
    }

    @Override
    public FolderResponse findById(FolderRequest entity) {
        return null;
    }

    @Override
    public List<FolderResponse> findAll() {
        return List.of();
    }
}

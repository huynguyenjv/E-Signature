package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.AssignedFieldRequest;
import com.backend.esignature.dto.responses.AssignedFieldResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignedFieldService implements ICRUDService<AssignedFieldRequest, AssignedFieldResponse, String> {
    @Override
    public AssignedFieldResponse create(AssignedFieldRequest entity) {
        return null;
    }

    @Override
    public AssignedFieldResponse update(AssignedFieldRequest entity, String id) {
        return null;
    }

    @Override
    public AssignedFieldResponse delete(String id) {
        return null;
    }

    @Override
    public AssignedFieldResponse findById(AssignedFieldRequest entity) {
        return null;
    }

    @Override
    public List<AssignedFieldResponse> findAll() {
        return List.of();
    }
}

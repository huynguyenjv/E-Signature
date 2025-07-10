package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.AnnotationRequest;
import com.backend.esignature.dto.responses.AnnotationResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnotationService implements ICRUDService<AnnotationRequest, AnnotationResponse,String> {
    @Override
    public AnnotationResponse create(AnnotationRequest entity) {
        return null;
    }

    @Override
    public AnnotationResponse update(AnnotationRequest entity, String id) {
        return null;
    }

    @Override
    public AnnotationResponse delete(String id) {
        return null;
    }

    @Override
    public AnnotationResponse findById(AnnotationRequest entity) {
        return null;
    }

    @Override
    public List<AnnotationResponse> findAll() {
        return List.of();
    }
}

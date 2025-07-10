package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.TemplateRequest;
import com.backend.esignature.dto.responses.TemplateResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService implements ICRUDService<TemplateRequest, TemplateResponse, String> {
    @Override
    public TemplateResponse create(TemplateRequest entity) {
        return null;
    }

    @Override
    public TemplateResponse update(TemplateRequest entity, String id) {
        return null;
    }

    @Override
    public TemplateResponse delete(String id) {
        return null;
    }

    @Override
    public TemplateResponse findById(TemplateRequest entity) {
        return null;
    }

    @Override
    public List<TemplateResponse> findAll() {
        return List.of();
    }
}

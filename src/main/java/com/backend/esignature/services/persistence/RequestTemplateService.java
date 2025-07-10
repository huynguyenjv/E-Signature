package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.RequestTemplateRequest;
import com.backend.esignature.dto.responses.RequestTemplateResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestTemplateService implements ICRUDService<RequestTemplateRequest, RequestTemplateResponse, String> {
    @Override
    public RequestTemplateResponse create(RequestTemplateRequest entity) {
        return null;
    }

    @Override
    public RequestTemplateResponse update(RequestTemplateRequest entity, String id) {
        return null;
    }

    @Override
    public RequestTemplateResponse delete(String id) {
        return null;
    }

    @Override
    public RequestTemplateResponse findById(RequestTemplateRequest entity) {
        return null;
    }

    @Override
    public List<RequestTemplateResponse> findAll() {
        return List.of();
    }
}

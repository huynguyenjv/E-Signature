package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.FormFieldRequest;
import com.backend.esignature.dto.responses.FormFieldResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormFieldService implements ICRUDService<FormFieldRequest, FormFieldResponse,String> {
    @Override
    public FormFieldResponse create(FormFieldRequest entity) {
        return null;
    }

    @Override
    public FormFieldResponse update(FormFieldRequest entity, String id) {
        return null;
    }

    @Override
    public FormFieldResponse delete(String id) {
        return null;
    }

    @Override
    public FormFieldResponse findById(FormFieldRequest entity) {
        return null;
    }

    @Override
    public List<FormFieldResponse> findAll() {
        return List.of();
    }
}

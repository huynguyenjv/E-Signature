package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.FormSubmissionRequest;
import com.backend.esignature.dto.responses.FormSubmissionResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormSubmissionService implements ICRUDService<FormSubmissionRequest, FormSubmissionResponse,String> {
    @Override
    public FormSubmissionResponse create(FormSubmissionRequest entity) {
        return null;
    }

    @Override
    public FormSubmissionResponse update(FormSubmissionRequest entity, String id) {
        return null;
    }

    @Override
    public FormSubmissionResponse delete(String id) {
        return null;
    }

    @Override
    public FormSubmissionResponse findById(FormSubmissionRequest entity) {
        return null;
    }

    @Override
    public List<FormSubmissionResponse> findAll() {
        return List.of();
    }
}

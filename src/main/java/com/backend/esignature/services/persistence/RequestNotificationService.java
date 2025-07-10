package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.FormSubmissionRequest;
import com.backend.esignature.dto.requests.RequestNotificationRequest;
import com.backend.esignature.dto.responses.FormSubmissionResponse;
import com.backend.esignature.dto.responses.RequestNotificationResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestNotificationService implements ICRUDService<RequestNotificationRequest, RequestNotificationResponse,String> {

    @Override
    public RequestNotificationResponse create(RequestNotificationRequest entity) {
        return null;
    }

    @Override
    public RequestNotificationResponse update(RequestNotificationRequest entity, String id) {
        return null;
    }

    @Override
    public RequestNotificationResponse delete(String id) {
        return null;
    }

    @Override
    public RequestNotificationResponse findById(RequestNotificationRequest entity) {
        return null;
    }

    @Override
    public List<RequestNotificationResponse> findAll() {
        return List.of();
    }
}

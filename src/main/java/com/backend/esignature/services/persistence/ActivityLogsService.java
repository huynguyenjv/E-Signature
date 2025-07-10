package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.ActivityLogRequest;
import com.backend.esignature.dto.responses.ActivityLogResponse;
import com.backend.esignature.entities.ActivityLogs;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogsService implements ICRUDService<ActivityLogRequest, ActivityLogResponse, String> {

    @Override
    public ActivityLogResponse create(ActivityLogRequest entity) {
        return null;
    }

    @Override
    public ActivityLogResponse update(ActivityLogRequest entity, String id) {
        return null;
    }

    @Override
    public ActivityLogResponse delete(String id) {
        return null;
    }

    @Override
    public ActivityLogResponse findById(ActivityLogRequest entity) {
        return null;
    }

    @Override
    public List<ActivityLogResponse> findAll() {
        return List.of();
    }
}

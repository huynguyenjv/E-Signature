package com.backend.esignature.controllers.audit;

import com.backend.esignature.dto.requests.ActivityLogRequest;
import com.backend.esignature.dto.responses.ActivityLogResponse;
import com.backend.esignature.exceptions.ResourceNotFoundException;
import com.backend.esignature.services.persistence.ActivityLogsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activity-log")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogsService activityLogsService;

    @PostMapping("/")
    public ResponseEntity<ActivityLogResponse> createActivityLog(@RequestBody ActivityLogRequest activityLogRequest) {
        if(activityLogRequest == null)
            throw new ResourceNotFoundException("ActivityLogRequest cannot be null");
        ActivityLogResponse activityLogResponse = activityLogsService.create(activityLogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityLogResponse);
    }

}

package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.CommentRequest;
import com.backend.esignature.dto.responses.CommentResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICRUDService<CommentRequest, CommentResponse,String> {
    @Override
    public CommentResponse create(CommentRequest entity) {
        return null;
    }

    @Override
    public CommentResponse update(CommentRequest entity, String id) {
        return null;
    }

    @Override
    public CommentResponse delete(String id) {
        return null;
    }

    @Override
    public CommentResponse findById(CommentRequest entity) {
        return null;
    }

    @Override
    public List<CommentResponse> findAll() {
        return List.of();
    }
}

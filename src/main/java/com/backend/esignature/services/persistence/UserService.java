package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.UserRequest;
import com.backend.esignature.dto.responses.UserResponse;
import com.backend.esignature.services.ICRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ICRUDService<UserRequest, UserResponse, String> {
    @Override
    public UserResponse create(UserRequest entity) {
        return null;
    }

    @Override
    public UserResponse update(UserRequest entity, String id) {
        return null;
    }

    @Override
    public UserResponse delete(String id) {
        return null;
    }

    @Override
    public UserResponse findById(UserRequest entity) {
        return null;
    }

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }
}

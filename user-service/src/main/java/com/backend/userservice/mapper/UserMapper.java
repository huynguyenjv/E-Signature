package com.backend.userservice.mapper;

import com.backend.userservice.dto.request.UserRequest;
import com.backend.userservice.dto.response.UserResponse;
import com.backend.userservice.entities.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse userRequestToUserResponse(UserRequest userRequest);
    UserResponse entityToUserResponse(Users users);
    Users responseToEntity(UserResponse userResponse);
    Users requestToEntity(UserRequest userRequest);

}

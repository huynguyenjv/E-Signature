package com.backend.authservice.mapper;

import com.backend.authservice.dto.response.UserResponse;
import com.backend.authservice.entities.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    UserResponse toResponse(Users user);

    Users toEntity(UserResponse userResponse);
}

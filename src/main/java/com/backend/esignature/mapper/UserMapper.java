package com.backend.esignature.mapper;

import com.backend.esignature.dto.responses.UserResponse;
import com.backend.esignature.entities.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    UserResponse toResponse(Users user);

    Users toEntity(UserResponse userResponse);
}

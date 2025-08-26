package com.backend.esignature.mapper;

import com.backend.esignature.dto.responses.UserResponse;
import com.backend.esignature.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {UserMapperHelper.class, DocumentMapperHelper.class})
public interface UserMapper {
    UserResponse toResponse(Users user);
    Users toEntity(UserResponse userResponse);
}
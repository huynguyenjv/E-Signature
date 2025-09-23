package com.backend.authservice.mapper;


import com.backend.authservice.dto.response.RoleResponse;
import com.backend.authservice.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(Role role);
}

package com.backend.esignature.mapper;

import com.backend.esignature.dto.responses.RoleResponse;
import com.backend.esignature.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(Role role);
}

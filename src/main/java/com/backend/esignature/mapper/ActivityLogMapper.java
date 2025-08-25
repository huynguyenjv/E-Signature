package com.backend.esignature.mapper;

import com.backend.esignature.dto.requests.ActivityLogRequest;
import com.backend.esignature.dto.responses.ActivityLogResponse;
import com.backend.esignature.entities.ActivityLogs;
import com.backend.esignature.mapper.helper.DocumentMapperHelper;
import com.backend.esignature.mapper.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class, DocumentMapperHelper.class})
public interface ActivityLogMapper {

    // Request -> Entity
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserById")
    @Mapping(target = "document", source = "documentId", qualifiedByName = "mapDocumentById")
    @Mapping(target = "createdAt", ignore = true) // đã xử lý bằng @PrePersist trong entity
    ActivityLogs toEntity(ActivityLogRequest request);

    // Entity -> Response
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "documentId", source = "document.id")
    ActivityLogResponse toResponse(ActivityLogs activityLogs);

    // Entity -> Request (ngược lại)
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "documentId", source = "document.id")
    ActivityLogRequest toRequest(ActivityLogs activityLogs);

    // Update entity từ request
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserById")
    @Mapping(target = "document", source = "documentId", qualifiedByName = "mapDocumentById")
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromRequest(ActivityLogRequest request, @MappingTarget ActivityLogs entity);
}

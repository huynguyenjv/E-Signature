package com.backend.esignature.services.persistence;

import com.backend.esignature.dto.requests.ActivityLogRequest;
import com.backend.esignature.dto.responses.ActivityLogResponse;
import com.backend.esignature.entities.ActivityLogs;
import com.backend.esignature.entities.Documents;
import com.backend.esignature.entities.Users;
import com.backend.esignature.exceptions.BadRequestException;
import com.backend.esignature.exceptions.ResourceNotFoundException;
import com.backend.esignature.mapper.ActivityLogMapper;
import com.backend.esignature.repositories.audit.ActivityLogsRepository;
import com.backend.esignature.repositories.document.DocumentsRepository;
import com.backend.esignature.repositories.user.UserRepository;
import com.backend.esignature.services.ICRUDService;
import com.backend.esignature.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

        @Slf4j
        @Service
        @RequiredArgsConstructor
        public class ActivityLogsService implements ICRUDService<ActivityLogRequest, ActivityLogResponse, String> {

            private final ActivityLogsRepository activityLogsRepository;
            private final UserRepository userRepository;
            private final DocumentsRepository documentsRepository;
            private final ActivityLogMapper mapper;
            private final ValidationUtils validationUtils;

            @Override
            @Transactional
            public ActivityLogResponse create(ActivityLogRequest request) {
                log.info("Creating ActivityLog entity");

                validationUtils.validateActivityLogRequest(request);

                try {
                    // Mapping entity to request dto
                    ActivityLogs activityLogsEntity = mapper.toEntity(request);

                    // Set user if provided
                    if (StringUtils.hasText(request.getUserId())) {
                        Users user = findUserById(request.getUserId());
                        activityLogsEntity.setUser(user);
                    }

                    // Set document if provided
                    if (StringUtils.hasText(request.getDocumentId())) {
                        Documents document = findDocumentById(request.getDocumentId());
                        activityLogsEntity.setDocument(document);
                    }

                    // Save entity into database
                    ActivityLogs savedEntity = activityLogsRepository.save(activityLogsEntity);

                    log.info("Successfully created ActivityLog with id: {}", savedEntity.getId());
                    return mapper.toResponse(savedEntity);

                } catch (ResourceNotFoundException | BadRequestException ex) {
                    log.error("Failed to create activity log: {}", ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while creating activity log", ex);
                    throw new RuntimeException("Failed to create activity log", ex);
                }
            }

            @Override
            @Transactional
            public ActivityLogResponse update(ActivityLogRequest request, String id) {
                log.info("Updating activity log with id: {}", id);

                validationUtils.validateActivityLogUpdateRequest(request, id);

                try {
                    ActivityLogs entity = findActivityLogById(id);

                    // Update basic fields
                    mapper.updateEntityFromRequest(request, entity);

                    // Update user if provided
                    if (StringUtils.hasText(request.getUserId())) {
                        log.info("Updating user for activity log with id: {}", id);
                        Users user = findUserById(request.getUserId());
                        entity.setUser(user);
                    }

                    // Update document if provided
                    if (StringUtils.hasText(request.getDocumentId())) {
                        log.info("Updating document for activity log with id: {}", id);
                        Documents document = findDocumentById(request.getDocumentId());
                        entity.setDocument(document);
                    }

                    ActivityLogs updatedEntity = activityLogsRepository.save(entity);

                    log.info("Successfully updated ActivityLog with id: {}", id);
                    return mapper.toResponse(updatedEntity);

                } catch (ResourceNotFoundException | BadRequestException ex) {
                    log.error("Failed to update activity log with id {}: {}", id, ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while updating activity log with id: {}", id, ex);
                    throw new RuntimeException("Failed to update activity log", ex);
                }
            }

            @Override
            @Transactional
            public ActivityLogResponse delete(String id) {
                log.info("Deleting activity log with id: {}", id);

                validationUtils.validateId(id, "ActivityLog");

                try {
                    ActivityLogs entity = findActivityLogById(id);
                    activityLogsRepository.delete(entity);

                    log.info("Successfully deleted ActivityLog with id: {}", id);

                } catch (ResourceNotFoundException ex) {
                    log.error("Failed to delete activity log with id {}: {}", id, ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while deleting activity log with id: {}", id, ex);
                    throw new RuntimeException("Failed to delete activity log", ex);
                }
                return null;
            }

            @Override
            public ActivityLogResponse findById(ActivityLogRequest request) {
                log.info("Finding activity log with id: {}", request.getId());

                validationUtils.validateActivityLogFindByIdRequest(request);

                try {
                    ActivityLogs entity = findActivityLogById(request.getId());

                    log.info("Successfully found ActivityLog with id: {}", request.getId());
                    return mapper.toResponse(entity);

                } catch (ResourceNotFoundException ex) {
                    log.error("Failed to find activity log with id {}: {}", request.getId(), ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while finding activity log with id: {}", request.getId(), ex);
                    throw new RuntimeException("Failed to find activity log", ex);
                }
            }

            @Override
            public List<ActivityLogResponse> findAll() {
                log.info("Finding all activity logs");

                try {
                    List<ActivityLogs> entityList = activityLogsRepository.findAll();

                    List<ActivityLogResponse> responseList = entityList.stream()
                            .map(mapper::toResponse)
                            .collect(Collectors.toList());

                    log.info("Successfully found {} activity logs", responseList.size());
                    return responseList;

                } catch (Exception ex) {
                    log.error("Unexpected error while finding all activity logs", ex);
                    throw new RuntimeException("Failed to find all activity logs", ex);
                }
            }

            // Private helper methods for validation
            private void validateRequest(ActivityLogRequest request) {
                if (request == null) {
                    throw new BadRequestException("ActivityLog request cannot be null");
                }

                // Add more specific validation as needed
                if (!StringUtils.hasText(request.getAction())) {
                    throw new BadRequestException("Action is required for activity log");
                }
            }

            private void validateUpdateRequest(ActivityLogRequest request, String id) {
                validateId(id);
                validateRequest(request);
            }

            private void validateFindByIdRequest(ActivityLogRequest request) {
                if (request == null) {
                    throw new BadRequestException("ActivityLog request cannot be null");
                }
                validateId(request.getId());
            }

            private void validateId(String id) {
                if (!StringUtils.hasText(id)) {
                    throw new BadRequestException("ActivityLog ID cannot be null or empty");
                }
            }

            // Private helper methods for finding entities
            private ActivityLogs findActivityLogById(String id) {
                return activityLogsRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("ActivityLog", "id", id));
            }

            private Users findUserById(String userId) {
                return userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
            }

            private Documents findDocumentById(String documentId) {
                return documentsRepository.findById(documentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Document", "id", documentId));
            }

            // Additional business methods (if needed)
            public List<ActivityLogResponse> findByUserId(String userId) {
                log.info("Finding activity logs by user id: {}", userId);

                validationUtils.validateId(userId, "User");

                try {
                    // First verify user exists
                    findUserById(userId);

                    List<ActivityLogs> entityList = activityLogsRepository.findByUserId(userId);

                    List<ActivityLogResponse> responseList = entityList.stream()
                            .map(mapper::toResponse)
                            .collect(Collectors.toList());

                    log.info("Successfully found {} activity logs for user: {}", responseList.size(), userId);
                    return responseList;

                } catch (ResourceNotFoundException ex) {
                    log.error("Failed to find activity logs by user id {}: {}", userId, ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while finding activity logs by user id: {}", userId, ex);
                    throw new RuntimeException("Failed to find activity logs by user", ex);
                }
            }

            public List<ActivityLogResponse> findByDocumentId(String documentId) {
                log.info("Finding activity logs by document id: {}", documentId);

                validationUtils.validateId(documentId, "Document");

                try {
                    // First verify document exists
                    findDocumentById(documentId);

                    List<ActivityLogs> entityList = activityLogsRepository.findByDocumentId(documentId);

                    List<ActivityLogResponse> responseList = entityList.stream()
                            .map(mapper::toResponse)
                            .collect(Collectors.toList());

                    log.info("Successfully found {} activity logs for document: {}", responseList.size(), documentId);
                    return responseList;

                } catch (ResourceNotFoundException ex) {
                    log.error("Failed to find activity logs by document id {}: {}", documentId, ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while finding activity logs by document id: {}", documentId, ex);
                    throw new RuntimeException("Failed to find activity logs by document", ex);
                }
            }

            @Transactional
            public void deleteByUserId(String userId) {
                log.info("Deleting activity logs by user id: {}", userId);

                validationUtils.validateId(userId, "User");

                try {
                    // First verify user exists
                    findUserById(userId);

                    activityLogsRepository.deleteByUserId(userId);

                    log.info("Successfully deleted activity logs for user: {}", userId);

                } catch (ResourceNotFoundException ex) {
                    log.error("Failed to delete activity logs by user id {}: {}", userId, ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while deleting activity logs by user id: {}", userId, ex);
                    throw new RuntimeException("Failed to delete activity logs by user", ex);
                }
            }

            @Transactional
            public void deleteByDocumentId(String documentId) {
                log.info("Deleting activity logs by document id: {}", documentId);

                validateId(documentId);

                try {
                    // First verify document exists
                    findDocumentById(documentId);

                    activityLogsRepository.deleteByDocumentId(documentId);

                    log.info("Successfully deleted activity logs for document: {}", documentId);

                } catch (ResourceNotFoundException ex) {
                    log.error("Failed to delete activity logs by document id {}: {}", documentId, ex.getMessage());
                    throw ex;
                } catch (Exception ex) {
                    log.error("Unexpected error while deleting activity logs by document id: {}", documentId, ex);
                    throw new RuntimeException("Failed to delete activity logs by document", ex);
                }
            }
        }
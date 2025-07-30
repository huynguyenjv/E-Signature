package com.backend.esignature.utils;

import com.backend.esignature.dto.requests.ActivityLogRequest;
import com.backend.esignature.exceptions.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ValidationUtils {

    // Generic validation methods
    public void validateId(String id) {
        if (!StringUtils.hasText(id)) {
            throw new BadRequestException("ID cannot be null or empty");
        }
    }

    public void validateId(String id, String entityName) {
        if (!StringUtils.hasText(id)) {
            throw new BadRequestException(entityName + " ID cannot be null or empty");
        }
    }

    public void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new BadRequestException(fieldName + " cannot be null");
        }
    }

    public void validateNotEmpty(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new BadRequestException(fieldName + " cannot be null or empty");
        }
    }

    // Specific validation methods for ActivityLogRequest
    public void validateActivityLogRequest(ActivityLogRequest request) {
        if (request == null) {
            throw new BadRequestException("ActivityLog request cannot be null");
        }

        // Validate required fields
        validateNotEmpty(request.getAction(), "Action");

        // Add more specific validation as needed
        if (request.getAction().length() > 255) {
            throw new BadRequestException("Action length cannot exceed 255 characters");
        }

        // Validate userId if provided
        if (StringUtils.hasText(request.getUserId())) {
            validateId(request.getUserId(), "User");
        }

        // Validate documentId if provided
        if (StringUtils.hasText(request.getDocumentId())) {
            validateId(request.getDocumentId(), "Document");
        }
    }

    public void validateActivityLogUpdateRequest(ActivityLogRequest request, String id) {
        validateId(id, "ActivityLog");
        validateActivityLogRequest(request);
    }

    public void validateActivityLogFindByIdRequest(ActivityLogRequest request) {
        if (request == null) {
            throw new BadRequestException("ActivityLog request cannot be null");
        }
        validateId(request.getId(), "ActivityLog");
    }

    // Generic validation methods for different entity types
    public void validateUserRequest(Object request) {
        if (request == null) {
            throw new BadRequestException("User request cannot be null");
        }
        // Add user-specific validation logic here
    }

    public void validateDocumentRequest(Object request) {
        if (request == null) {
            throw new BadRequestException("Document request cannot be null");
        }
        // Add document-specific validation logic here
    }

    // Email validation
    public void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new BadRequestException("Email cannot be null or empty");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }
    }

    // Phone number validation
    public void validatePhoneNumber(String phoneNumber) {
        if (StringUtils.hasText(phoneNumber)) {
            String phoneRegex = "^[+]?[0-9]{10,15}$";
            if (!phoneNumber.matches(phoneRegex)) {
                throw new BadRequestException("Invalid phone number format");
            }
        }
    }

    // Date validation
    public void validateDateRange(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new BadRequestException("Start date cannot be after end date");
            }
        }
    }

    // File validation
    public void validateFileSize(long fileSize, long maxSizeInBytes) {
        if (fileSize > maxSizeInBytes) {
            throw new BadRequestException("File size exceeds maximum allowed size");
        }
    }

    public void validateFileExtension(String fileName, String... allowedExtensions) {
        if (!StringUtils.hasText(fileName)) {
            throw new BadRequestException("File name cannot be null or empty");
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        boolean isValidExtension = false;

        for (String allowedExt : allowedExtensions) {
            if (fileExtension.equals(allowedExt.toLowerCase())) {
                isValidExtension = true;
                break;
            }
        }

        if (!isValidExtension) {
            throw new BadRequestException("Invalid file extension. Allowed extensions: " +
                    String.join(", ", allowedExtensions));
        }
    }

    // Pagination validation
    public void validatePagination(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new BadRequestException("Page size must be greater than 0");
        }
        if (size > 100) {
            throw new BadRequestException("Page size cannot exceed 100");
        }
    }

    // Password validation
    public void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new BadRequestException("Password cannot be null or empty");
        }

        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }

        if (password.length() > 128) {
            throw new BadRequestException("Password cannot exceed 128 characters");
        }

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("Password must contain at least one uppercase letter");
        }

        // Check for at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            throw new BadRequestException("Password must contain at least one lowercase letter");
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            throw new BadRequestException("Password must contain at least one digit");
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new BadRequestException("Password must contain at least one special character");
        }
    }

    // URL validation
    public void validateUrl(String url) {
        if (StringUtils.hasText(url)) {
            String urlRegex = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
            if (!url.matches(urlRegex)) {
                throw new BadRequestException("Invalid URL format");
            }
        }
    }

    // UUID validation
    public void validateUUID(String uuid) {
        if (StringUtils.hasText(uuid)) {
            String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
            if (!uuid.matches(uuidRegex)) {
                throw new BadRequestException("Invalid UUID format");
            }
        }
    }

    // Numeric validation
    public void validatePositiveNumber(Number number, String fieldName) {
        if (number == null) {
            throw new BadRequestException(fieldName + " cannot be null");
        }
        if (number.doubleValue() <= 0) {
            throw new BadRequestException(fieldName + " must be a positive number");
        }
    }

    public void validateRange(Number value, Number min, Number max, String fieldName) {
        if (value == null) {
            throw new BadRequestException(fieldName + " cannot be null");
        }
        if (value.doubleValue() < min.doubleValue() || value.doubleValue() > max.doubleValue()) {
            throw new BadRequestException(fieldName + " must be between " + min + " and " + max);
        }
    }

    // String length validation
    public void validateStringLength(String value, int minLength, int maxLength, String fieldName) {
        if (value == null) {
            throw new BadRequestException(fieldName + " cannot be null");
        }
        if (value.length() < minLength) {
            throw new BadRequestException(fieldName + " must be at least " + minLength + " characters long");
        }
        if (value.length() > maxLength) {
            throw new BadRequestException(fieldName + " cannot exceed " + maxLength + " characters");
        }
    }

    // Collection validation
    public void validateNotEmpty(java.util.Collection<?> collection, String fieldName) {
        if (collection == null || collection.isEmpty()) {
            throw new BadRequestException(fieldName + " cannot be null or empty");
        }
    }

    public void validateCollectionSize(java.util.Collection<?> collection, int maxSize, String fieldName) {
        if (collection != null && collection.size() > maxSize) {
            throw new BadRequestException(fieldName + " cannot contain more than " + maxSize + " items");
        }
    }
}
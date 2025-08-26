package com.backend.esignature.exceptions;

import com.backend.esignature.dto.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ApiResponse.<ErrorResponse>builder()
                .code("404")
                .message("Not Found")
                .data(errorResponse)
                .build();
    }

    // Handle BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<ErrorResponse> handleBadRequestException(
            BadRequestException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ApiResponse.<ErrorResponse>builder()
                .code("400")
                .message("Bad Request")
                .data(errorResponse)
                .build();
    }

    // Handle UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ApiResponse.<ErrorResponse>builder()
                .code("401")
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data(errorResponse)
                .build();
    }

    // Handle ForbiddenException
    @ExceptionHandler(ForbiddenException.class)
    public ApiResponse<ErrorResponse> handleForbiddenException(
            ForbiddenException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ApiResponse.<ErrorResponse>builder()
                .code("403")
                .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .data(errorResponse)
                .build();
    }

    // Handle Validation Errors (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Input validation failed",
                request.getDescription(false).replace("uri=", "")
        );

        List<ErrorResponse.ValidationError> validationErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.add(new ErrorResponse.ValidationError(fieldName, errorMessage));
        });

        errorResponse.setValidationErrors(validationErrors);

        return ApiResponse.<ErrorResponse>builder()
                .code("400")
                .message("Validation Failed")
                .data(errorResponse)
                .build();
    }

    // Handle Constraint Violation Exception (Path Variable/Request Parameter validation)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Constraint Violation",
                "Validation failed",
                request.getDescription(false).replace("uri=", "")
        );

        List<ErrorResponse.ValidationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse.ValidationError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .collect(Collectors.toList());

        errorResponse.setValidationErrors(validationErrors);

        return ApiResponse.<ErrorResponse>builder()
                .code("400")
                .message("Constraint Violation")
                .data(errorResponse)
                .build();
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return ApiResponse.<ErrorResponse>builder()
                .code("400")
                .message("Bad Request")
                .data(errorResponse)
                .build();
    }

    // Handle Generic Exception
    @ExceptionHandler(Exception.class)
    public ApiResponse<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred",
                request.getDescription(false).replace("uri=", "")
        );

        return ApiResponse.<ErrorResponse>builder()
                .code("500")
                .message("Internal Server Error")
                .data(errorResponse)
                .build();
    }
}

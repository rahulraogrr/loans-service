package com.ing.exceptions;

import com.ing.exceptions.custom.ResourceNotFoundException;
import com.ing.exceptions.custom.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler {
    // ValidationException handler
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(final ValidationException e, final HttpServletRequest request) {
        log.error("Validation EXCEPTION: ", e.getMessage());
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .calledURI(request.getRequestURI())
                .message("Validation Error")
                .errors(e.getErrors())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // HttpMediaTypeNotSupportedException handler
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e, final HttpServletRequest request) {
        log.error("HttpMediaTypeNotSupportedException EXCEPTION: ", e);
        return new ResponseEntity<>(ApiErrorResponse.builder()
                .calledURI(request.getRequestURI())
                .message("Request object is null or blank")
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException e, final HttpServletRequest request) {
        log.error("ResourceNotFoundException EXCEPTION: ", e);
        return new ResponseEntity<>(ApiErrorResponse.builder()
                .calledURI(request.getRequestURI())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    // Generic Exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(final Exception e, final HttpServletRequest request) {
        log.error("UNHANDLED EXCEPTION: ", e.getMessage());
        return new ResponseEntity<>(ApiErrorResponse.builder()
                .calledURI(request.getRequestURI())
                .message("Internal Server Error. We have logged the error, and we will work hard to make it right")
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

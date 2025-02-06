package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Global exception handler for handling custom exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link DuplicateEmailException} and returns a response with a 409 Conflict status.
     *
     * @param exception the exception thrown when a duplicate email is encountered
     * @return a {@link ResponseEntity} containing the exception message and HTTP status
     */
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link NotFoundRoleException} and returns a response with a 404 Not Found status.
     *
     * @param exception the exception thrown when a requested role is not found
     * @return a {@link ResponseEntity} containing the exception message and HTTP status
     */
    public ResponseEntity<String> handleNotFoundRoleException(NotFoundRoleException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

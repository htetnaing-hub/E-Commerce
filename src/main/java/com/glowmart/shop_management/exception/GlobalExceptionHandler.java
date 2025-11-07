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
     * Handles {@link NotFoundException} and returns a response with a 404 Not Found status.
     *
     * @param exception the exception thrown when a requested role is not found
     * @return a {@link ResponseEntity} containing the exception message and HTTP status
     */
    public ResponseEntity<String> handleNotFoundRoleException(NotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link NotValidException} and returns a response with a 400 Bad Request status.
     * <p>
     * This method is invoked when an invalid name is provided in a request,
     * ensuring clients receive a clear error message along with the appropriate
     * HTTP status code.
     * </p>
     *
     * @param exception the exception thrown when a provided name is not valid
     * @return a {@link ResponseEntity} containing the exception message and HTTP status
     */
    public ResponseEntity<String> handleNotValidNameException(NotValidException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

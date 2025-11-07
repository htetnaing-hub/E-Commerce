package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a duplicate resource or entity is encountered.
 * <p>
 * This exception is annotated with {@link ResponseStatus} to automatically
 * return an HTTP 409 (Conflict) status code when thrown in a Spring
 * web application.
 * </p>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateException extends RuntimeException{

    /**
     * Constructs a new {@code DuplicateException} with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public DuplicateException(String message){
        super(message);
    }

}

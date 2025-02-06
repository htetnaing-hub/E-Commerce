package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when attempting to register a user with an email
 * that is already in use.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException{

    /**
     * Constructs a new DuplicateEmailException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public DuplicateEmailException(String message){
        super(message);
    }

}

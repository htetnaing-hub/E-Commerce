package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested role is not found in the system.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundRoleException extends RuntimeException{

    /**
     * Constructs a new NotFoundRoleException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NotFoundRoleException(String message){
        super(message);
    }

}

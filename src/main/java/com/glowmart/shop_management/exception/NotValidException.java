package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a provided input value is not valid.
 * <p>
 * This exception is annotated with {@link ResponseStatus} to automatically
 * return an HTTP 400 (Bad Request) status code when thrown in a Spring
 * web application. It is typically used to indicate invalid request
 * parameters or data that fails validation.
 * </p>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidException extends RuntimeException{

    /**
     * Constructs a new {@code NotValidException} with the specified detail message.
     *
     * @param message the detail message that explains why the input is invalid
     */
    public NotValidException(String message){
        super(message);
    }

}

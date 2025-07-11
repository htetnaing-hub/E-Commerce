package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidNameException extends RuntimeException{

    public NotValidNameException(String message){
        super(message);
    }

}

package com.glowmart.shop_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCategoryException extends RuntimeException{

    public DuplicateCategoryException(String message){
        super(message);
    }

}

package com.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PostalCodeNotValidException extends Exception {
    public PostalCodeNotValidException(String message){
        super(message);
    }
}

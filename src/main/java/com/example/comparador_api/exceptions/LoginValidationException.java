package com.example.comparador_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginValidationException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoginValidationException(String message) {
        super(message);
    }
}

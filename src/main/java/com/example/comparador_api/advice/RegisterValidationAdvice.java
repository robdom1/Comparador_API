package com.example.comparador_api.advice;

import com.example.comparador_api.Utils.ErrorMessage;
import com.example.comparador_api.Utils.ValidationErrorMessage;
import com.example.comparador_api.exceptions.LoginValidationException;
import com.example.comparador_api.exceptions.RegisterValidationException;
import com.example.comparador_api.exceptions.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestControllerAdvice
public class RegisterValidationAdvice {

    @ExceptionHandler(value = RegisterValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleRegisterValidationException(RegisterValidationException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ValidationErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                errors.size(),
                errors,
                request.getDescription(false));
    }

    @ExceptionHandler(value = LoginValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleLoginValidationException(LoginValidationException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
}

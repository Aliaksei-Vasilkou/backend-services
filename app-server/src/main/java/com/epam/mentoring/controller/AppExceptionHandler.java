package com.epam.mentoring.controller;

import com.epam.mentoring.domain.model.ErrorResponse;
import com.epam.mentoring.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.text.MessageFormat;
import java.util.Objects;

@Log4j2
@ControllerAdvice
public class AppExceptionHandler extends ResponseStatusExceptionHandler {

    private static final String SOURCE_APPLICATION = "APPLICATION_NAME";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("USER_NOT_FOUND")
                .message(ex.getMessage())
                .source(SOURCE_APPLICATION)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> handleValidationExceptions(WebExchangeBindException ex) {
        log.error(ex.getMessage(), ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        Objects.requireNonNull(fieldError);
        String messageDescription = MessageFormat.format("Field [{0}] has error: {1}", fieldError.getField(),
                fieldError.getDefaultMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("VALIDATION_FAILED")
                .message(messageDescription)
                .source(SOURCE_APPLICATION)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

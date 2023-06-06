package ru.otus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.dto.AppError;
import ru.otus.exception.ResourceNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handlerException(ResourceNotFoundException e) {
        AppError appError = new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        return ResponseEntity.ok(appError);
    }

}

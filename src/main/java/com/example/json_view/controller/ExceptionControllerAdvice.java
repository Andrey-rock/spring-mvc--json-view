package com.example.json_view.controller;

import com.example.json_view.exception.UserNoSuchException;
import com.example.json_view.model.UserError;
import io.swagger.v3.oas.annotations.Hidden;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Hidden
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UserNoSuchException.class)
    public ResponseEntity<UserError> handleUserNoSuchException(@NotNull UserNoSuchException e) {
        UserError bankError = new UserError("404", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bankError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserError> handleMethodArgumentNotValidException(@NotNull MethodArgumentNotValidException e) {
        UserError bankError = new UserError("400", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bankError);
    }
}

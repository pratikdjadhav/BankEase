package com.bankease.bankease.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String >> notFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error : ",e.getMessage()));

    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String,String>> duplicate(DuplicateResourceException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Error : ",e.getMessage()));
    }

    @ExceptionHandler({InsufficientBalanceException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String,String>> badRequest(RuntimeException e){
        return ResponseEntity.badRequest().body(Map.of("Error",e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException e){
        String msg = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(Map.of("Error : ",msg));
    }

}

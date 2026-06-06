package com.aleks.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Map<String, Object> handleUserExists(
      UserAlreadyExistsException ex
  ) {

    return Map.of(
        "timestamp", Instant.now(),
        "message", ex.getMessage()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(
      MethodArgumentNotValidException ex
  ) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Map<String, Object> handleInvalidCredentials(
      InvalidCredentialsException ex
  ) {

    return Map.of(
        "timestamp", Instant.now(),
        "message", ex.getMessage()
    );
  }

  @ExceptionHandler(TokenExpiredException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Map<String, Object> handleTokenExpired(
      TokenExpiredException ex
  ) {

    return Map.of(
        "timestamp", Instant.now(),
        "message", ex.getMessage()
    );
  }
}
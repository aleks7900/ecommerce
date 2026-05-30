package com.aleks.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(
      InventoryNotFoundException.class
  )
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNotFound(
      InventoryNotFoundException ex
  ) {

    return Map.of(
        "timestamp", Instant.now(),
        "status", 404,
        "message", ex.getMessage()
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(
      HttpStatus.INTERNAL_SERVER_ERROR
  )
  public Map<String, Object> handleGeneric(
      Exception ex
  ) {

    return Map.of(
        "timestamp", Instant.now(),
        "status", 500,
        "message", ex.getMessage()
    );
  }
}
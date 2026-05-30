package com.aleks.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(
      OrderNotFoundException.class
  )
  public ResponseEntity<Map<String, Object>>
  handleOrderNotFound(
      OrderNotFoundException ex
  ) {

    Map<String, Object> response =
        new HashMap<>();

    response.put(
        "timestamp",
        Instant.now()
    );

    response.put(
        "status",
        HttpStatus.NOT_FOUND.value()
    );

    response.put(
        "error",
        "Order Not Found"
    );

    response.put(
        "message",
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(response);
  }

  @ExceptionHandler(
      MethodArgumentNotValidException.class
  )
  public ResponseEntity<Map<String, Object>>
  handleValidation(
      MethodArgumentNotValidException ex
  ) {

    Map<String, Object> response =
        new HashMap<>();

    response.put(
        "timestamp",
        Instant.now()
    );

    response.put(
        "status",
        HttpStatus.BAD_REQUEST.value()
    );

    response.put(
        "error",
        "Validation Error"
    );

    response.put(
        "message",
        ex.getBindingResult()
            .getAllErrors()
            .get(0)
            .getDefaultMessage()
    );

    return ResponseEntity
        .badRequest()
        .body(response);
  }

  @ExceptionHandler(
      Exception.class
  )
  public ResponseEntity<Map<String, Object>>
  handleGeneric(
      Exception ex
  ) {

    Map<String, Object> response =
        new HashMap<>();

    response.put(
        "timestamp",
        Instant.now()
    );

    response.put(
        "status",
        HttpStatus.INTERNAL_SERVER_ERROR.value()
    );

    response.put(
        "error",
        "Internal Server Error"
    );

    response.put(
        "message",
        ex.getMessage()
    );

    return ResponseEntity
        .status(
            HttpStatus.INTERNAL_SERVER_ERROR
        )
        .body(response);
  }
}

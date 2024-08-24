package com.al_najah.tatweer.controller;

import com.al_najah.tatweer.dto.ApiErrorResponse;
import com.al_najah.tatweer.dto.ApiValidationErrorResponse;
import com.al_najah.tatweer.exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiValidationErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, WebRequest request) {
    List<ApiValidationErrorResponse.FieldError> fieldErrors = new ArrayList<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              fieldErrors.add(new ApiValidationErrorResponse.FieldError(fieldName, errorMessage));
            });
    ApiValidationErrorResponse errorResponse =
        new ApiValidationErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            LocalDateTime.now(),
            request.getDescription(false),
            "Validation failed",
            fieldErrors);
    return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(EntityAlreadyExistsException.class)
  public ResponseEntity<ApiErrorResponse> handleEntityAlreadyExistException(
      EntityAlreadyExistsException ex, WebRequest request) {
    ApiErrorResponse errorResponse =
        new ApiErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            request.getDescription(false),
            ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
      EntityNotFoundException ex, WebRequest request) {
    ApiErrorResponse errorResponse =
        new ApiErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(),
            request.getDescription(false),
            ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
          IllegalArgumentException ex, WebRequest request) {
    ApiErrorResponse errorResponse =
            new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now(),
                    request.getDescription(false),
                    ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGeneralExceptions(
      Exception ex, WebRequest request) {
    ApiErrorResponse errorResponse =
        new ApiErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            request.getDescription(false),
            ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

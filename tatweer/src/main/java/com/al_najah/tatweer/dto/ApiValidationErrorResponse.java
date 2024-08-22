package com.al_najah.tatweer.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ApiValidationErrorResponse(
    int statusCode, LocalDateTime timeStamp, String path, String message, List<FieldError> errors) {
  public record FieldError(String field, String message) {}
}

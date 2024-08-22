package com.al_najah.tatweer.dto;

import java.time.LocalDateTime;

public record ApiErrorResponse(
    int statusCode, LocalDateTime timeStamp, String path, String message) {}

package com.al_najah.tatweer.dto;

import java.time.LocalDateTime;

public record HabitRecord(
    String name, String description, UserRecord user, LocalDateTime startDate) {}

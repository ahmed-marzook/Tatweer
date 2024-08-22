package com.al_najah.tatweer.dto.habit;

import com.al_najah.tatweer.dto.user.UserRecord;
import java.time.LocalDateTime;

public record HabitRecord(
    String name, String description, UserRecord user, LocalDateTime startDate) {}

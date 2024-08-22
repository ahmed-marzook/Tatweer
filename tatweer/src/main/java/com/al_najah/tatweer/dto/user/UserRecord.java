package com.al_najah.tatweer.dto.user;

import com.al_najah.tatweer.dto.habit.HabitRecord;
import java.util.List;

public record UserRecord(
    String userName, String firstName, String lastName, String email, List<HabitRecord> habits) {}

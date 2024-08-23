package com.al_najah.tatweer.dto.habit;

import com.al_najah.tatweer.enums.HabitFrequencyType;
import jakarta.validation.constraints.*;

public record HabitRequestRecord(
    @NotBlank(message = "Habit name is required")
        @Size(max = 100, message = "Habit name must be at most 100 characters long")
        String title,
    @Size(max = 500, message = "Description must be at most 500 characters long")
        String description,
    @NotNull(message = "Frequency type is required") HabitFrequencyType habitFrequencyType,
    @Min(value = 1, message = "Frequency count must be at least 1")
        @Max(value = 31, message = "Frequency count must be at most 31")
        Integer frequencyCount) {
  public HabitRequestRecord {
    if ((habitFrequencyType == HabitFrequencyType.WEEKLY
            || habitFrequencyType == HabitFrequencyType.MONTHLY)
        && frequencyCount == null) {
      throw new IllegalArgumentException(
          "Frequency count is required for WEEKLY and MONTHLY frequency types");
    }
  }
}

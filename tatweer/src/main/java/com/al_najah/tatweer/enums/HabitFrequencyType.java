package com.al_najah.tatweer.enums;

import com.al_najah.tatweer.converters.AbstractEnumConverter;

public enum HabitFrequencyType {
  DAILY,
  WEEKLY,
  MONTHLY,
  SPECIFIC_DAYS;

  public boolean isDaily() {
    return this == DAILY;
  }

  public boolean isWeekly() {
    return this == WEEKLY;
  }

  public boolean isMonthly() {
    return this == MONTHLY;
  }

  public boolean isSpecificDays() {
    return this == SPECIFIC_DAYS;
  }

  public static class Converter extends AbstractEnumConverter<HabitFrequencyType> {
    public Converter() {
      super(HabitFrequencyType.class);
    }
  }
}

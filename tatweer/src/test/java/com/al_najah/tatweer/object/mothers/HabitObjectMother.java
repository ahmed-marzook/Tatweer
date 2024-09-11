package com.al_najah.tatweer.object.mothers;

import com.al_najah.tatweer.dto.habit.HabitRequestRecord;
import com.al_najah.tatweer.entity.Habit;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.enums.HabitFrequencyType;

import java.time.LocalDateTime;

public class HabitObjectMother {
    public static Habit createCoinCollectingHabit(User user) {
        return Habit.builder()
                .habitId(1L)
                .title("Coin Collecting")
                .description("Collect 100 coins daily for extra lives")
                .habitFrequencyType(HabitFrequencyType.DAILY)
                .frequency(1)
                .user(user)
                .startDate(LocalDateTime.parse("2023-01-01T08:00:00"))
                .createdAt(LocalDateTime.parse("2023-01-01T08:00:00"))
                .updatedAt(LocalDateTime.parse("2023-01-01T08:00:00"))
                .build();
    }

    public static HabitRequestRecord createCoinCollectingHabitRequest = new HabitRequestRecord(
            "Coin Collecting",
            "Collect 100 coins daily for extra lives",
            HabitFrequencyType.DAILY,
            1);

    public static Habit createMasterSwordTraining(User user) {
        return Habit.builder()
                .habitId(2L)
                .title("Master Sword Training")
                .description("Practice sword skills to defeat Ganon")
                .habitFrequencyType(HabitFrequencyType.WEEKLY)
                .frequency(3)
                .user(user)
                .startDate(LocalDateTime.parse("2023-01-02T09:00:00"))
                .createdAt(LocalDateTime.parse("2023-01-02T09:00:00"))
                .updatedAt(LocalDateTime.parse("2023-01-02T09:00:00"))
                .build();
    }
}

package com.al_najah.tatweer.service;

import com.al_najah.tatweer.dto.habit.HabitRequestRecord;
import com.al_najah.tatweer.entity.Habit;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.repository.HabitRepository;
import com.al_najah.tatweer.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;

public class HabitService {

  private final HabitRepository habitRepository;

  private final UserRepository userRepository;

  public HabitService(HabitRepository habitRepository, UserRepository userRepository) {
    this.habitRepository = habitRepository;
    this.userRepository = userRepository;
  }

  public Long createHabit(HabitRequestRecord habitRequestRecord, UUID uuid) {
    User user =
        userRepository
            .findByUserUuid(uuid)
            .orElseThrow(() -> new EntityNotFoundException("User not found with UUID: " + uuid));

    Habit habit =
        Habit.builder()
            .title(habitRequestRecord.title())
            .description(habitRequestRecord.description())
            .habitFrequencyType(habitRequestRecord.habitFrequencyType())
            .frequency(habitRequestRecord.frequencyCount())
            .user(user)
            .build();
    return habitRepository.save(habit).getHabitId();
  }
}

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

  public Habit createHabit(HabitRequestRecord habitRequestRecord, UUID uuid) {
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
    return habitRepository.save(habit);
  }

  public void deleteHabit(Long habitId, UUID uuid) {
        userRepository
            .findByUserUuid(uuid)
            .orElseThrow(() -> new EntityNotFoundException("User not found with UUID: " + uuid));
    habitRepository.deleteById(habitId);
  }

  public Habit updateHabit(HabitRequestRecord habitRequestRecord, Long habitId, UUID uuid) {
    userRepository
        .findByUserUuid(uuid)
        .orElseThrow(() -> new EntityNotFoundException("User not found with UUID: " + uuid));
    Habit habit =
        habitRepository
            .findById(habitId)
            .orElseThrow(() -> new EntityNotFoundException("Habit not found with ID: " + habitId));

    habit.setTitle(habitRequestRecord.title());
    habit.setDescription(habitRequestRecord.description());
    habit.setHabitFrequencyType(habitRequestRecord.habitFrequencyType());
    habit.setFrequency(habitRequestRecord.frequencyCount());
    return habitRepository.save(habit);
  }
}

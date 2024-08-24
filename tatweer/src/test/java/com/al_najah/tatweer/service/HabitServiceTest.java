package com.al_najah.tatweer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.al_najah.tatweer.dto.habit.HabitRequestRecord;
import com.al_najah.tatweer.entity.Habit;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.enums.HabitFrequencyType;
import com.al_najah.tatweer.repository.HabitRepository;
import com.al_najah.tatweer.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class HabitServiceTest {

  private HabitRepository habitRepository;

  private UserRepository userRepository;

  private HabitService habitService;

  public HabitServiceTest() {
    this.habitRepository = mock(HabitRepository.class);
    this.userRepository = mock(UserRepository.class);
    this.habitService = new HabitService(habitRepository, userRepository);
  }

  @Test
  void createHabit_Success() {
    UUID uuid = UUID.randomUUID();
    User user = new User();
    HabitRequestRecord habitRequestRecord =
        new HabitRequestRecord("Test Habit", "Description", HabitFrequencyType.DAILY, 1);
    Habit savedHabit = new Habit();

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.of(user));
    when(habitRepository.save(any(Habit.class))).thenReturn(savedHabit);

    Habit result = habitService.createHabit(habitRequestRecord, uuid);

    assertThat(result).isNotNull();
    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository).save(any(Habit.class));
  }

  @Test
  void createHabit_UserNotFound() {
    UUID uuid = UUID.randomUUID();
    HabitRequestRecord habitRequestRecord =
        new HabitRequestRecord("Test Habit", "Description", HabitFrequencyType.DAILY, 1);

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> habitService.createHabit(habitRequestRecord, uuid))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("User not found");

    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository, never()).save(any(Habit.class));
  }

  @Test
  void deleteHabit_Success() {
    UUID uuid = UUID.randomUUID();
    Long habitId = 1L;
    User user = new User();

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.of(user));

    assertThatCode(() -> habitService.deleteHabit(habitId, uuid)).doesNotThrowAnyException();

    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository).deleteById(habitId);
  }

  @Test
  void deleteHabit_UserNotFound() {
    UUID uuid = UUID.randomUUID();
    Long habitId = 1L;

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> habitService.deleteHabit(habitId, uuid))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("User not found");

    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository, never()).deleteById(any());
  }

  @Test
  void updateHabit_Success() {
    UUID uuid = UUID.randomUUID();
    Long habitId = 1L;
    User user = new User();
    Habit existingHabit = new Habit();
    HabitRequestRecord habitRequestRecord =
        new HabitRequestRecord(
            "Updated Habit", "Updated Description", HabitFrequencyType.WEEKLY, 2);

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.of(user));
    when(habitRepository.findById(habitId)).thenReturn(Optional.of(existingHabit));
    when(habitRepository.save(any(Habit.class))).thenReturn(existingHabit);

    Habit result = habitService.updateHabit(habitRequestRecord, habitId, uuid);

    assertThat(result).isNotNull();
    assertThat(existingHabit)
        .extracting(
            Habit::getTitle,
            Habit::getDescription,
            Habit::getHabitFrequencyType,
            Habit::getFrequency)
        .containsExactly("Updated Habit", "Updated Description", HabitFrequencyType.WEEKLY, 2);

    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository).findById(habitId);
    verify(habitRepository).save(existingHabit);
  }

  @Test
  void updateHabit_UserNotFound() {
    UUID uuid = UUID.randomUUID();
    Long habitId = 1L;
    HabitRequestRecord habitRequestRecord =
        new HabitRequestRecord(
            "Updated Habit", "Updated Description", HabitFrequencyType.WEEKLY, 2);

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> habitService.updateHabit(habitRequestRecord, habitId, uuid))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("User not found");

    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository, never()).findById(any());
    verify(habitRepository, never()).save(any());
  }

  @Test
  void updateHabit_HabitNotFound() {
    UUID uuid = UUID.randomUUID();
    Long habitId = 1L;
    User user = new User();
    HabitRequestRecord habitRequestRecord =
        new HabitRequestRecord(
            "Updated Habit", "Updated Description", HabitFrequencyType.WEEKLY, 2);

    when(userRepository.findByUserUuid(uuid)).thenReturn(Optional.of(user));
    when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> habitService.updateHabit(habitRequestRecord, habitId, uuid))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Habit not found");

    verify(userRepository).findByUserUuid(uuid);
    verify(habitRepository).findById(habitId);
    verify(habitRepository, never()).save(any());
  }
}

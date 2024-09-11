package com.al_najah.tatweer.service.component;

import com.al_najah.tatweer.config.BaseComponentTest;
import com.al_najah.tatweer.dto.habit.HabitRequestRecord;
import com.al_najah.tatweer.entity.Habit;
import com.al_najah.tatweer.object.mothers.UserObjectMother;
import com.al_najah.tatweer.repository.HabitRepository;
import com.al_najah.tatweer.repository.UserRepository;
import com.al_najah.tatweer.service.HabitService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static com.al_najah.tatweer.object.mothers.HabitObjectMother.createCoinCollectingHabitRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@Sql(value = "/repository/habit/create_habits.sql")
public class HabitServiceComponentTest extends BaseComponentTest {

    @Autowired private HabitService habitService;

    @Autowired private HabitRepository habitRepository;

    @Autowired private UserRepository userRepository;

    @Test
    void createHabit_shouldCreateAndReturnHabit() {
        HabitRequestRecord testHabitRequest = createCoinCollectingHabitRequest;
        Habit result = habitService.createHabit(testHabitRequest, UserObjectMother.MarioUserValues.USER_UUID);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(testHabitRequest.title());
        assertThat(result.getUser().getUserUuid()).isEqualTo(testUuid);
        assertThat(habitRepository.findById(result.getId())).isPresent();
    }

    @Test
    void createHabit_shouldThrowException_whenUserNotFound() {
        UUID nonExistentUuid = UUID.randomUUID();

        assertThatThrownBy(() -> habitService.createHabit(testHabitRequest, nonExistentUuid))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User not found with UUID: " + nonExistentUuid);
    }

    @Test
    void deleteHabit_shouldDeleteHabit() {
        Habit habit = habitService.createHabit(testHabitRequest, testUuid);
        Long habitId = habit.getId();

        habitService.deleteHabit(habitId, testUuid);

        assertThat(habitRepository.findById(habitId)).isEmpty();
    }

    @Test
    void deleteHabit_shouldThrowException_whenUserNotFound() {
        UUID nonExistentUuid = UUID.randomUUID();

        assertThatThrownBy(() -> habitService.deleteHabit(1L, nonExistentUuid))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User not found with UUID: " + nonExistentUuid);
    }

    @Test
    void updateHabit_shouldUpdateAndReturnHabit() {
        Habit habit = habitService.createHabit(testHabitRequest, testUuid);
        Long habitId = habit.getId();

        HabitRequestRecord updateRequest = new HabitRequestRecord(
                "Updated Habit",
                "Updated Description",
                "WEEKLY",
                2
        );

        Habit result = habitService.updateHabit(updateRequest, habitId, testUuid);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(updateRequest.title());
        assertThat(result.getDescription()).isEqualTo(updateRequest.description());
        assertThat(result.getHabitFrequencyType()).isEqualTo(updateRequest.habitFrequencyType());
        assertThat(result.getFrequency()).isEqualTo(updateRequest.frequencyCount());
    }

    @Test
    void updateHabit_shouldThrowException_whenHabitNotFound() {
        Long nonExistentHabitId = 999L;

        assertThatThrownBy(() -> habitService.updateHabit(testHabitRequest, nonExistentHabitId, testUuid))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Habit not found with ID: " + nonExistentHabitId);
    }
}

package com.al_najah.tatweer.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.al_najah.tatweer.dto.habit.HabitRequestRecord;
import com.al_najah.tatweer.entity.Habit;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.enums.HabitFrequencyType;
import com.al_najah.tatweer.service.HabitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HabitController.class)
public class HabitControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private HabitService habitService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void createHabit_Success() throws Exception {
    UUID userUuid = UUID.randomUUID();
    User user = new User();
    HabitRequestRecord habitRequest =
        new HabitRequestRecord(
            "Morning Yoga", "15 minutes every morning", HabitFrequencyType.DAILY, 1);
    Habit createdHabit =
        new Habit(
            1L,
            "Morning Yoga",
            "15 minutes every morning",
            HabitFrequencyType.DAILY,
            1,
            user,
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now());

    when(habitService.createHabit(any(HabitRequestRecord.class), any(UUID.class)))
        .thenReturn(createdHabit);

    mockMvc
        .perform(
            post("/api/habit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(habitRequest))
                .header("User-UUID", userUuid.toString()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$['Habit ID: ']").value("1"))
        .andExpect(jsonPath("$['message: ']").value("Habit Morning Yoga successfully created."));
  }

  @Test
  void updateHabit_Success() throws Exception {
    UUID userUuid = UUID.randomUUID();
    long habitId = 1L;
    User user = new User();
    HabitRequestRecord habitRequest =
        new HabitRequestRecord(
            "Updated Yoga", "20 minutes every morning", HabitFrequencyType.DAILY, 1);
    Habit updatedHabit =
        new Habit(
            1L,
            "Updated Yoga",
            "20 minutes every morning",
            HabitFrequencyType.DAILY,
            1,
            user,
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now());

    when(habitService.updateHabit(any(HabitRequestRecord.class), any(Long.class), any(UUID.class)))
        .thenReturn(updatedHabit);

    mockMvc
        .perform(
            put("/api/habit/" + habitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(habitRequest))
                .header("User-UUID", userUuid.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['Habit ID: ']").value("1"))
        .andExpect(jsonPath("$['message: ']").value("Habit Updated Yoga successfully updated."));
  }

  @Test
  void deleteHabit_Success() throws Exception {
    UUID userUuid = UUID.randomUUID();
    long habitId = 1L;

    mockMvc
        .perform(delete("/api/habit/" + habitId).header("User-UUID", userUuid.toString()))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$['Habit ID: ']").value("1"))
        .andExpect(jsonPath("$['message: ']").value("Habit 1 deleted."));
  }

  @Test
  void createHabit_ValidationFailure() throws Exception {
    UUID userUuid = UUID.randomUUID();

    // Create a JSON string directly instead of using HabitRequestRecord
    String invalidRequestJson =
        """
            {
                "title": "",
                "description": "This description is way too long. It exceeds the 500 character limit by repeating this sentence many times. This description is way too long. It exceeds the 500 character limit by repeating this sentence many times. This description is way too long. It exceeds the 500 character limit by repeating this sentence many times. This description is way too long. It exceeds the 500 character limit by repeating this sentence many times. This description is way too long. It exceeds the 500 character limit by repeating this sentence many times. This description is way too long. It exceeds the 500 character limit by repeating this sentence many times.",
                "habitFrequencyType": "DAILY",
                "frequencyCount": 32
            }
            """;

    mockMvc
        .perform(
            post("/api/habit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson)
                .header("User-UUID", userUuid.toString()))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.statusCode").value(422))
        .andExpect(jsonPath("$.message").value("Validation failed"))
        .andExpect(jsonPath("$.path").value("uri=/api/habit"))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.errors", hasSize(3)))
        .andExpect(
            jsonPath("$.errors[*].field")
                .value(containsInAnyOrder("title", "description", "frequencyCount")))
        .andExpect(
            jsonPath("$.errors[*].message")
                .value(
                    containsInAnyOrder(
                        "Description must be at most 500 characters long",
                        "Habit name is required",
                        "Frequency count must be at most 31")));
  }

  @Test
  void updateHabit_EntityNotFound() throws Exception {
    UUID userUuid = UUID.randomUUID();
    long habitId = 1L;
    HabitRequestRecord habitRequest =
        new HabitRequestRecord("Updated Habit", "Description", HabitFrequencyType.DAILY, 1);

    when(habitService.updateHabit(any(HabitRequestRecord.class), any(Long.class), any(UUID.class)))
        .thenThrow(new EntityNotFoundException("Habit not found with ID: 1"));

    mockMvc
        .perform(
            put("/api/habit/" + habitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(habitRequest))
                .header("User-UUID", userUuid.toString()))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Habit not found with ID: 1"));
  }

  @Test
  void createHabit_IllegalArgument() throws Exception {
    UUID userUuid = UUID.randomUUID();
    String invalidRequestJson =
        """
           {
             "title": "New Habit",
             "description": "Description",
             "habitFrequencyType": "WEEKLY"
            }
           """;

    mockMvc
        .perform(
            post("/api/habit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson)
                .header("User-UUID", userUuid.toString()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(
            jsonPath("$.message")
                .value("Frequency count is required for WEEKLY and MONTHLY frequency types"));
  }
}

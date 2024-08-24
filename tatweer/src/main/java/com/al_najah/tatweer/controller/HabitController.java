package com.al_najah.tatweer.controller;

import com.al_najah.tatweer.dto.habit.HabitRequestRecord;
import com.al_najah.tatweer.entity.Habit;
import com.al_najah.tatweer.service.HabitService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habit")
public class HabitController {

  private final HabitService habitService;

  @Autowired
  public HabitController(HabitService habitService) {
    this.habitService = habitService;
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> createHabit(
      @RequestBody @Valid HabitRequestRecord habitRequest, @RequestHeader("User-UUID") UUID uuid) {
    Habit habit = habitService.createHabit(habitRequest, uuid);
    Map<String, String> response = new HashMap<>();
    String message = String.format("Habit %s successfully created.", habit.getTitle());
    response.put("Habit ID: ", habit.getHabitId().toString());
    response.put("message: ", message);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{habitId}")
  public ResponseEntity<Map<String, String>> updateHabit(
      @PathVariable Long habitId,
      @Valid @RequestBody HabitRequestRecord habitRequest,
      @RequestHeader("User-UUID") UUID userUuid) {
    Habit habit = habitService.updateHabit(habitRequest, habitId, userUuid);
    Map<String, String> response = new HashMap<>();
    String message = String.format("Habit %s successfully updated.", habit.getTitle());
    response.put("Habit ID: ", habit.getHabitId().toString());
    response.put("message: ", message);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{habitId}")
  public ResponseEntity<Map<String, String>> deleteHabit(
      @PathVariable Long habitId, @RequestHeader("User-UUID") UUID userUuid) {
    habitService.deleteHabit(habitId, userUuid);
    Map<String, String> response = new HashMap<>();
    String message = String.format("Habit %s deleted.", habitId);
    response.put("Habit ID: ", habitId.toString());
    response.put("message: ", message);
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }
}

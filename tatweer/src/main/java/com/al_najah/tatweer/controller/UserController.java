package com.al_najah.tatweer.controller;

import com.al_najah.tatweer.dto.user.UserCreateDTO;
import com.al_najah.tatweer.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> createUser(
      @RequestBody @Valid UserCreateDTO userCreateDTO) {
    UUID userUuid = userService.addNewUser(userCreateDTO);
    Map<String, String> response = new HashMap<>();
    String message = String.format("User %s successfully created.", userCreateDTO.userName());
    response.put("User UUID: ", userUuid.toString());
    response.put("message: ", message);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}

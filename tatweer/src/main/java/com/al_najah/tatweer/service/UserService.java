package com.al_najah.tatweer.service;

import com.al_najah.tatweer.dto.user.UserCreateDTO;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.exceptions.EntityAlreadyExistsException;
import com.al_najah.tatweer.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;

public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UUID addNewUser(UserCreateDTO userCreateDTO) {
    if (userRepository.existsByUsername(userCreateDTO.userName())) {
      throw new EntityAlreadyExistsException(
          String.format(
              "Username '%s' is already taken. Please provide a different username to proceed.",
              userCreateDTO.userName()));
    }
    if (userRepository.existsByEmail(userCreateDTO.email())) {
      throw new EntityAlreadyExistsException(
          String.format(
              "Email '%s' is already registered. Please provide a different email to proceed.",
              userCreateDTO.email()));
    }
    User newUser =
        User.builder()
            .email(userCreateDTO.email())
            .firstName(userCreateDTO.firstName())
            .lastName(userCreateDTO.lastName())
            .username(userCreateDTO.userName())
            .userUuid(UUID.randomUUID())
            .build();

    return userRepository.save(newUser).getUserUuid();
  }
}

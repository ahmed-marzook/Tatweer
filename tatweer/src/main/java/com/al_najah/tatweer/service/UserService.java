package com.al_najah.tatweer.service;

import com.al_najah.tatweer.dto.UserRecord;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.repository.UserRepository;
import jakarta.transaction.Transactional;

public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void addNewUser(UserRecord userRecord) {
    User newUser =
        User.builder()
            .email(userRecord.email())
            .firstName(userRecord.firstName())
            .lastName(userRecord.lastName())
            .username(userRecord.userName())
            .build();

    userRepository.save(newUser);
  }
}

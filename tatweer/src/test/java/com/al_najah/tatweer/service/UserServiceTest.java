package com.al_najah.tatweer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.al_najah.tatweer.dto.UserRecord;
import com.al_najah.tatweer.repository.UserRepository;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

  private final UserRepository userRepository;

  private final UserService userService;

  public UserServiceTest() {
    this.userRepository = mock(UserRepository.class);
    this.userService = new UserService(userRepository);
  }

  @Test
  void addNewUser() {
    UserRecord userRecord = new UserRecord("Test", "Test", "testLast", "test@email.com", null);

    userService.addNewUser(userRecord);

    verify(userRepository).save(any());
  }
}

package com.al_najah.tatweer.service;

import static org.junit.jupiter.api.Assertions.*;

import com.al_najah.tatweer.config.BaseComponentTest;
import com.al_najah.tatweer.dto.UserRecord;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceComponentTest extends BaseComponentTest {

  @Autowired private UserService userService;

  @Autowired private UserRepository userRepository;

  @Test
  void testCreateUser() {
    UserRecord userRecord =
        new UserRecord("testuser", "Ahmed", "Marzook", "test@example.com", null);

    userService.addNewUser(userRecord);
    Optional<User> user = userRepository.findByEmail("test@example.com");

    assertTrue(user.isPresent());
    assertEquals("testuser", user.get().getUsername());
    assertEquals("test@example.com", user.get().getEmail());
  }
}

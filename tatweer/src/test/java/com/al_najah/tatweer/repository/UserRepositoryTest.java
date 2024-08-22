package com.al_najah.tatweer.repository;

import com.al_najah.tatweer.config.BaseRepositoryTest;
import com.al_najah.tatweer.dto.user.UserRecord;
import com.al_najah.tatweer.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends BaseRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  void findUserByEmail_valid() {
    UserRecord userRecord = new UserRecord("marza004", "Ahmed", "Marzook", "ahmed@email.com", null);
    User user = new User();
    user.setUsername("testuser1");
    user.setFirstName("Ahmed");
    user.setLastName("Marzook");
    user.setEmail("test1@example.com");
    userRepository.save(user);
  }
}

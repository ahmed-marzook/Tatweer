package com.al_najah.tatweer.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.al_najah.tatweer.config.BaseRepositoryTest;
import com.al_najah.tatweer.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql(value = "/repository/user/create_users.sql")
public class UserRepositoryTest extends BaseRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  void findUserByEmail_valid() {
    Optional<User> userActual = userRepository.findByEmail("mario@mushroom.kingdom");

    assertThat(userActual)
        .isPresent()
        .hasValueSatisfying(
            u -> {
              assertThat(u.getUsername()).isEqualTo("superMario");
              assertThat(u.getFirstName()).isEqualTo("Mario");
              assertThat(u.getLastName()).isEqualTo("Mario");
              assertThat(u.getEmail()).isEqualTo("mario@mushroom.kingdom");
              assertThat(u.getUserUuid())
                  .isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
            });
  }

  @Test
  void existsUserByEmail_valid() {
    boolean emailExist = userRepository.existsByEmail("samus@galactic.fed");

    assertTrue(emailExist);
  }

  @Test
  void existsUserByUsername_valid() {
    boolean emailExist = userRepository.existsByUsername("masterChief117");

    assertTrue(emailExist);
  }
}

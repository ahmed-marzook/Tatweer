package com.al_najah.tatweer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.al_najah.tatweer.dto.user.UserCreateDTO;
import com.al_najah.tatweer.entity.User;
import com.al_najah.tatweer.exceptions.EntityAlreadyExistsException;
import com.al_najah.tatweer.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

  private final UserRepository userRepository;

  private final UserService userService;

  public UserServiceTest() {
    this.userRepository = mock(UserRepository.class);
    this.userService = new UserService(userRepository);
  }

  @Test
  void addNewUser_ShouldSaveUserAndReturnUUID() {
    UserCreateDTO userCreateDTO = new UserCreateDTO("john.doe", "John", "Doe", "john@example.com");
    User savedUser =
        User.builder()
            .email(userCreateDTO.email())
            .firstName(userCreateDTO.firstName())
            .lastName(userCreateDTO.lastName())
            .username(userCreateDTO.userName())
            .userUuid(UUID.randomUUID())
            .build();

    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    UUID result = userService.addNewUser(userCreateDTO);

    assertThat(result).isNotNull().isEqualTo(savedUser.getUserUuid());
  }

  @Test
  void addNewUser_ShouldThrowUsernameAlreadyExistsException() {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("existing_user", "John", "Doe", "john@example.com");
    when(userRepository.existsByUsername(userCreateDTO.userName())).thenReturn(true);

    assertThatThrownBy(() -> userService.addNewUser(userCreateDTO))
        .isInstanceOf(EntityAlreadyExistsException.class)
        .hasMessage(
            "Username 'existing_user' is already taken. Please provide a different username to proceed.");
  }

  @Test
  void addNewUser_ShouldThrowEmailAlreadyExistsException() {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("john.doe", "John", "Doe", "existing@example.com");
    when(userRepository.existsByEmail(userCreateDTO.email())).thenReturn(true);

    assertThatThrownBy(() -> userService.addNewUser(userCreateDTO))
        .isInstanceOf(EntityAlreadyExistsException.class)
        .hasMessage(
            "Email 'existing@example.com' is already registered. Please provide a different email to proceed.");
  }
}

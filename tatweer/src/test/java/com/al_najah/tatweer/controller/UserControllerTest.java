package com.al_najah.tatweer.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.al_najah.tatweer.dto.user.UserCreateDTO;
import com.al_najah.tatweer.exceptions.EntityAlreadyExistsException;
import com.al_najah.tatweer.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void testCreateUser_Success() throws Exception {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("testuser", "John", "Doe", "john.doe@example.com");
    UUID mockUuid = UUID.randomUUID();

    when(userService.addNewUser(any(UserCreateDTO.class))).thenReturn(mockUuid);

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$['User UUID: ']").value(mockUuid.toString()))
        .andExpect(jsonPath("$['message: ']").value("User testuser successfully created."));
  }

  @Test
  public void testCreateUser_ValidationFailure() throws Exception {
    UserCreateDTO userCreateDTO = new UserCreateDTO("", "", "", "invalid-email");

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.statusCode").value(422))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.path").value("uri=/api/user"))
        .andExpect(jsonPath("$.message").value("Validation failed"))
        .andExpect(jsonPath("$.errors", hasSize(8)))
        .andExpect(
            jsonPath("$.errors[*].field")
                .value(
                    containsInAnyOrder(
                        "userName",
                        "firstName",
                        "lastName",
                        "email",
                        "firstName",
                        "lastName",
                        "userName",
                        "userName")))
        .andExpect(
            jsonPath("$.errors[*].message")
                .value(
                    containsInAnyOrder(
                        "Username is required",
                        "First name is required",
                        "Last name is required",
                        "Email should be valid",
                        "First name must be between 1 and 50 characters",
                        "Last name must be between 1 and 50 characters",
                        "Username must be between 3 and 50 characters",
                        "Username must not contain white spaces")));
  }

  @Test
  public void testCreateUser_UserNameTooShort() throws Exception {
    UserCreateDTO userCreateDTO = new UserCreateDTO("ab", "John", "Doe", "john.doe@example.com");

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.statusCode").value(422))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.path").value("uri=/api/user"))
        .andExpect(jsonPath("$.message").value("Validation failed"))
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors[0].field").value("userName"))
        .andExpect(
            jsonPath("$.errors[0].message").value("Username must be between 3 and 50 characters"));
  }

  @Test
  public void testCreateUser_UserNameWithWhitespace() throws Exception {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("test user", "John", "Doe", "john.doe@example.com");

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.statusCode").value(422))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.path").value("uri=/api/user"))
        .andExpect(jsonPath("$.message").value("Validation failed"))
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors[0].field").value("userName"))
        .andExpect(jsonPath("$.errors[0].message").value("Username must not contain white spaces"));
  }

  @Test
  public void testCreateUser_UserNameAlreadyExists() throws Exception {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("existinguser", "John", "Doe", "john.doe@example.com");

    when(userService.addNewUser(any(UserCreateDTO.class)))
        .thenThrow(new EntityAlreadyExistsException("Username 'existinguser' is already taken"));

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.statusCode").value(500))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.path").value("uri=/api/user"))
        .andExpect(jsonPath("$.message").value("Username 'existinguser' is already taken"));
  }

  @Test
  public void testCreateUser_EmailAlreadyExists() throws Exception {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("newuser", "John", "Doe", "existing@example.com");

    when(userService.addNewUser(any(UserCreateDTO.class)))
        .thenThrow(
            new EntityAlreadyExistsException("Email 'existing@example.com' is already registered"));

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.statusCode").value(500))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.path").value("uri=/api/user"))
        .andExpect(
            jsonPath("$.message").value("Email 'existing@example.com' is already registered"));
  }

  @Test
  public void testCreateUser_UnexpectedException() throws Exception {
    UserCreateDTO userCreateDTO =
        new UserCreateDTO("newuser", "John", "Doe", "john.doe@example.com");

    when(userService.addNewUser(any(UserCreateDTO.class)))
        .thenThrow(new RuntimeException("Unexpected error occurred"));

    mockMvc
        .perform(
            post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.statusCode").value(500))
        .andExpect(jsonPath("$.timeStamp").exists())
        .andExpect(jsonPath("$.path").value("uri=/api/user"))
        .andExpect(jsonPath("$.message").value("Unexpected error occurred"));
  }
}

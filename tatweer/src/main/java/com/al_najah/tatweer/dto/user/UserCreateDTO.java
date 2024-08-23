package com.al_najah.tatweer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
    @NotBlank(message = "Username is required")
        @Pattern(regexp = "^\\S+$", message = "Username must not contain white spaces")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String userName,
    @NotBlank(message = "First name is required")
        @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
        String firstName,
    @NotBlank(message = "Last name is required")
        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
        String lastName,
    @NotBlank(message = "Email is required") @Email(message = "Email should be valid")
        String email) {}

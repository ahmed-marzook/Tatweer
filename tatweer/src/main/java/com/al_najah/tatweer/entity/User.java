package com.al_najah.tatweer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotBlank
  @Column(unique = true)
  private String username;

  @NotBlank private String firstName;

  @NotBlank private String lastName;

  @NotBlank
  @Email
  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Habit> habits;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}

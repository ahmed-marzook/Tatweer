package com.al_najah.tatweer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table(name = "habits")
public class Habit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long habit_id;

  @NotBlank private String name;

  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private LocalDateTime startDate;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}

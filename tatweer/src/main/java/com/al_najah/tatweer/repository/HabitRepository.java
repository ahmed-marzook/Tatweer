package com.al_najah.tatweer.repository;

import com.al_najah.tatweer.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {}

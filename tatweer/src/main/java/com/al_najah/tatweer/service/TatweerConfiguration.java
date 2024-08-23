package com.al_najah.tatweer.service;

import com.al_najah.tatweer.repository.HabitRepository;
import com.al_najah.tatweer.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TatweerConfiguration {

  @Bean
  UserService userService(UserRepository userRepository) {
    return new UserService(userRepository);
  }

  @Bean
  HabitService habitService(HabitRepository habitRepository, UserRepository userRepository) {
    return new HabitService(habitRepository, userRepository);
  }
}

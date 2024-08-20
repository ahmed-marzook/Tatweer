package com.al_najah.tatweer.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public abstract class BaseComponentTest {

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    PostgresTestContainer.registerPgProperties(registry);
  }
}

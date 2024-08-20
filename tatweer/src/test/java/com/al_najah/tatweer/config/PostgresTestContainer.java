package com.al_najah.tatweer.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgresTestContainer {

  protected static final PostgreSQLContainer<?> postgres =
          new PostgreSQLContainer<>("postgres:14")
                  .withDatabaseName("testdb")
                  .withUsername("testuser")
                  .withPassword("testpass");

  static {
    postgres.start();
  }

  public static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }
}

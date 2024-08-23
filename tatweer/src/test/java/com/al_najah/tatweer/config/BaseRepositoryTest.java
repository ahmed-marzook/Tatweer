package com.al_najah.tatweer.config;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
    value = "/database_scripts/clear_tables.sql",
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class BaseRepositoryTest {

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    PostgresTestContainer.registerPgProperties(registry);
  }
}

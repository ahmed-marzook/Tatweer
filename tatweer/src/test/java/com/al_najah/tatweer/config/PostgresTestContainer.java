package com.al_najah.tatweer.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class PostgresTestContainer {

  private static final String POSTGRES_NETWORK_ALIAS = "postgres-test";

  @Container
  protected static final PostgreSQLContainer<?> postgres =
          new PostgreSQLContainer<>("postgres:14")
                  .withDatabaseName("testdb")
                  .withUsername("testuser")
                  .withPassword("testpass")
                  .withNetworkAliases(POSTGRES_NETWORK_ALIAS);

  static {
    postgres.start();
  }

  public static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url",
            () -> String.format("jdbc:postgresql://%s:%d/%s",
                    POSTGRES_NETWORK_ALIAS,
                    PostgreSQLContainer.POSTGRESQL_PORT,
                    postgres.getDatabaseName()));
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }
}

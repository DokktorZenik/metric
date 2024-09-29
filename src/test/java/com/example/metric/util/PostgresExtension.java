package com.example.metric.util;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresExtension implements BeforeAllCallback {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:11-alpine")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("schema.sql");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        POSTGRES.start();

        System.setProperty("spring.data.postgres.port", POSTGRES.getMappedPort(5432).toString());
        System.setProperty("spring.data.postgres.host", "127.0.0.1");
    }
}

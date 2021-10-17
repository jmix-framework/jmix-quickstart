package com.company.jmixpm.integration.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSqlExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer postgreSQLContainer;

    @Override
    public void beforeAll(ExtensionContext context) {
        postgreSQLContainer = new PostgreSQLContainer()
                .withDatabaseName("postgres-test-db")
                .withUsername("test")
                .withPassword("pass");
        postgreSQLContainer.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        postgreSQLContainer.stop();
    }
}

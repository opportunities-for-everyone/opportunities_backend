package com.project.opportunities.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgresContainer extends PostgreSQLContainer<CustomPostgresContainer> {
    private static final String DB_IMAGE = "postgres:15";

    private static CustomPostgresContainer container;

    private CustomPostgresContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomPostgresContainer getInstance() {
        if (container == null) {
            container = new CustomPostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", container.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", container.getUsername());
        System.setProperty("TEST_DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
    }
}

package com.example.sptest.config;

import org.testcontainers.containers.MySQLContainer;

public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {

    private static final String DB_IMAGE = "mysql:8.0.33";
    private static CustomMySqlContainer container;

    private static final int HOST_PORT = 3306;
    private CustomMySqlContainer() {
        super("mysql:8.0.33");
        withDatabaseName("bookApi");
        withUsername("test");
        withPassword("test");
        // Фіксуємо хостовий порт 3307 → контейнерний 3306
        addFixedExposedPort(HOST_PORT, 3306);
    }

    public static synchronized CustomMySqlContainer getInstance() {
        if (container == null) {
          container = new CustomMySqlContainer();
        }
        return container;
    }

    @Override
    public void start() {
        System.setProperty("spring.datasource.url", getJdbcUrl());
        System.setProperty("spring.datasource.username", getUsername());
        System.setProperty("spring.datasource.password", getPassword());
    }

    @Override
    public void stop() {
        // не зупиняємо контейнер між тестами
    }
}

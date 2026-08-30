package com.suraev.OnlineBokingManagement.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class TestConfig {


    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("postgres");


    @DynamicPropertySource
    static void setProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
    }


}
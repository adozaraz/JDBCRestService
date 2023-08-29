package org.restservice.repositories;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;


import static org.junit.jupiter.api.Assertions.*;

class EnrollmentRepositoryImplTest {
    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }

    private static final String DB_NAME = "RESTService";

    private static final String USER = "adozaraz";

    private static final String PASSWORD = "12345";

    @Rule
    private PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"))
            .withDatabaseName(DB_NAME)
            .withUsername(USER)
            .withPassword(PASSWORD);

    private EnrollmentRepository enrollmentRepository;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        String url = postgres.getJdbcUrl();
        Properties props = new Properties();
        props.put("user", postgres.getUsername());
        props.put("password", postgres.getPassword());
        Connection conn = DriverManager.getConnection(url, props);
        enrollmentRepository = new EnrollmentRepositoryImpl(conn);
    }

    @Test
    void create() {
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByStudentId() {
    }

    @Test
    void deleteByLearningClassId() {
    }

    @Test
    void getByStudent() {
    }

    @Test
    void getByLearningClass() {
    }
}
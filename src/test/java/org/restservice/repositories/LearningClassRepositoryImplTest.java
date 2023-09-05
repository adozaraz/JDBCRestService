package org.restservice.repositories;

import org.junit.After;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.restservice.DbConnection;
import org.restservice.entities.LearningClass;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class LearningClassRepositoryImplTest {
    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
        try {
            DbConnection con = DbConnection.getInstance();
            properties = con.getProps();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Container
    public PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"))
            .withDatabaseName(properties.getProperty("db.database"))
            .withUsername(properties.getProperty("db.username"))
            .withPassword(properties.getProperty("db.password"))
            .withInitScript("db/db.sql");

    private LearningClassRepository learningClassRepository;

    private static Properties properties;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        DbConnection con = DbConnection.getInstance();
        con.connectToServer();

        learningClassRepository = new LearningClassRepositoryImpl();
    }

    @AfterEach
    public void after() throws SQLException {
        postgres.stop();
    }

    @Test
    void create() {
        LearningClass learningClass = new LearningClass("Test", "Test description");

        boolean added = learningClassRepository.create(learningClass);
        assertTrue(added);
    }

    @Test
    void read() {
        LearningClass expectedClass = new LearningClass("Test", "Test description");
        learningClassRepository.create(expectedClass);

        Optional<LearningClass> actualClass = learningClassRepository.read(UUID.fromString(expectedClass.getLearningClassId()));
        assertTrue(actualClass.isPresent());
        assertEquals(expectedClass, actualClass.get());
    }

    @Test
    void update() {
        UUID uuid = UUID.randomUUID();
        LearningClass expectedClass = new LearningClass(uuid, "Test", "Test desc");
        LearningClass actualClass = new LearningClass(uuid, "Test", "Lol");
        learningClassRepository.create(actualClass);
        actualClass.setDescription("Test desc");
        boolean result = learningClassRepository.update(actualClass);
        assertTrue(result);
        Optional<LearningClass> fromDb = learningClassRepository.read(uuid);
        assertTrue(fromDb.isPresent());
        actualClass = fromDb.get();
        assertEquals(expectedClass, actualClass);
    }

    @Test
    void delete() throws SQLException {
        LearningClass expectedClass = new LearningClass("Test", "Test description");
        learningClassRepository.create(expectedClass);

        boolean deleted = learningClassRepository.delete(expectedClass);
        assertTrue(deleted);

        Optional<LearningClass> actual = learningClassRepository.read(UUID.fromString(expectedClass.getLearningClassId()));
        assertFalse(actual.isPresent());
    }


}
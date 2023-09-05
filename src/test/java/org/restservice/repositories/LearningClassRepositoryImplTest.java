package org.restservice.repositories;

import org.junit.After;
import org.junit.Ignore;
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
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class LearningClassRepositoryImplTest {
    private static final Logger log = Logger.getLogger("LearningClassRepositoryImplTest");

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

    public PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"))
            .withUsername(properties.getProperty("db.user"))
            .withPassword(properties.getProperty("db.password"))
            .withDatabaseName("TEST_DATABASE")
            .withInitScript("db/db.sql");

    private LearningClassRepository learningClassRepository;

    private static Properties properties;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        DbConnection con = DbConnection.getInstance();
        con.changeURL(postgres.getJdbcUrl());

        learningClassRepository = new LearningClassRepositoryImpl();
    }

    @AfterEach
    public void after() throws SQLException {
        DbConnection con = DbConnection.getInstance();
        con.closeConnection();
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
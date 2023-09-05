package org.restservice.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.restservice.DbConnection;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;


import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class EnrollmentRepositoryImplTest {
    static {
        // Postgres JDBC driver uses JUL; disable it to avoid annoying, irrelevant, stderr logs during connection testing
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
    }

    private static final String DB_NAME = "RESTService";

    private static final String USER = "adozaraz";

    private static final String PASSWORD = "12345";

    @Container
    private PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"))
            .withDatabaseName(DB_NAME)
            .withUsername(USER)
            .withPassword(PASSWORD)
            .withInitScript("db/db.sql");

    private EnrollmentRepository enrollmentRepository;

    private StudentRepository studentRepository;

    private LearningClassRepository learningClassRepository;

    private Student student;
    private LearningClass learningClass;

    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        DbConnection con = DbConnection.getInstance();
        Properties props = new Properties();
        props.put("db.url", postgres.getJdbcUrl());
        props.put("db.user", postgres.getUsername());
        props.put("db.password", postgres.getPassword());
        con.changeProps(props);
        enrollmentRepository = new EnrollmentRepositoryImpl();
        studentRepository = new StudentRepositoryImpl();
        learningClassRepository = new LearningClassRepositoryImpl();
        student = new Student("Boradulin", "Nikita");
        learningClass = new LearningClass("Test", "School");
    }

    @AfterEach
    public void after() throws SQLException {
        postgres.stop();
        conn.close();
    }

    @Test
    void create() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        boolean result = enrollmentRepository.create(student.getStudentId(), learningClass.getLearningClassId());
        assertTrue(result);
    }

    @Test
    void deleteByStudentId() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(student.getStudentId(), learningClass.getLearningClassId());
        boolean result = enrollmentRepository.deleteByStudentId(student.getStudentId());
        assertTrue(result);
    }

    @Test
    void deleteByLearningClassId() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(student.getStudentId(), learningClass.getLearningClassId());
        boolean result = enrollmentRepository.deleteByLearningClassId(learningClass.getLearningClassId());
        assertTrue(result);
    }

    @Test
    void getByStudent() {
        Set<LearningClass> learningClasses = new HashSet<>();
        learningClasses.add(learningClass);
        student.setLearningClasses(learningClasses);

        studentRepository.create(student);
        learningClassRepository.create(learningClass);

        Optional<Student> actualStudent = enrollmentRepository.getByStudent(UUID.fromString(student.getStudentId()));
        assertTrue(actualStudent.isPresent());
        assertEquals(student, actualStudent.get());
    }

    @Test
    void getByLearningClass() {
        Set<Student> attendingStudents = new HashSet<>();
        attendingStudents.add(student);
        learningClass.setAttendingStudents(attendingStudents);

        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(student.getStudentId(), learningClass.getLearningClassId());

        Optional<LearningClass> actualLearningClass = enrollmentRepository.getByLearningClass(UUID.fromString(learningClass.getLearningClassId()));
        assertTrue(actualLearningClass.isPresent());
        assertEquals(learningClass, actualLearningClass.get());
    }
}
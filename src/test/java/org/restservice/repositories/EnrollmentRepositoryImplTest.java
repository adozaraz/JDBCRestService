package org.restservice.repositories;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.restservice.entities.Enrollment;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
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

    private Enrollment enrollment;

    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        String url = postgres.getJdbcUrl();
        Properties props = new Properties();
        props.put("user", postgres.getUsername());
        props.put("password", postgres.getPassword());
        conn = DriverManager.getConnection(url, props);
        enrollmentRepository = new EnrollmentRepositoryImpl(conn);
        studentRepository = new StudentRepositoryImpl(conn);
        learningClassRepository = new LearningClassRepositoryImpl(conn);
        student = new Student("Boradulin", "Nikita");
        learningClass = new LearningClass("Test", "School");
        enrollment = new Enrollment(student, learningClass);
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
        boolean result = enrollmentRepository.create(enrollment);
        assertTrue(result);
    }

    @Test
    void read() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(enrollment);
        Optional<Enrollment> actualEnrollment = enrollmentRepository.read(UUID.fromString(enrollment.getEnrollmentId()));
        assertTrue(actualEnrollment.isPresent());
        assertEquals(enrollment, actualEnrollment.get());
    }

    @Test
    void update() {
        studentRepository.create(student);
        Student substitute = new Student("Test", "Work");
        studentRepository.create(substitute);
        learningClassRepository.create(learningClass);
        Enrollment testEnrollment = new Enrollment(enrollment.getEnrollmentId(), substitute, learningClass);
        enrollmentRepository.create(testEnrollment);
        testEnrollment.setStudent(student);
        boolean result = enrollmentRepository.update(testEnrollment);
        assertTrue(result);

        Optional<Enrollment> actualEnrollment = enrollmentRepository.read(UUID.fromString(testEnrollment.getEnrollmentId()));
        assertTrue(actualEnrollment.isPresent());
        assertEquals(enrollment, actualEnrollment.get());
    }

    @Test
    void delete() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(enrollment);
        boolean result = enrollmentRepository.delete(enrollment);
        assertTrue(result);

        Optional<Enrollment> actualEnrollment = enrollmentRepository.read(UUID.fromString(enrollment.getEnrollmentId()));
        assertFalse(actualEnrollment.isPresent());
    }

    @Test
    void deleteByStudentId() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(enrollment);
        boolean result = enrollmentRepository.deleteByStudentId(student.getStudentId());
        assertTrue(result);

        Optional<Enrollment> actualEnrollment = enrollmentRepository.read(UUID.fromString(enrollment.getEnrollmentId()));
        assertFalse(actualEnrollment.isPresent());
    }

    @Test
    void deleteByLearningClassId() {
        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(enrollment);
        boolean result = enrollmentRepository.deleteByLearningClassId(learningClass.getLearningClassId());
        assertTrue(result);

        Optional<Enrollment> actualEnrollment = enrollmentRepository.read(UUID.fromString(enrollment.getEnrollmentId()));
        assertFalse(actualEnrollment.isPresent());
    }

    @Test
    void getByStudent() {
        Set<LearningClass> learningClasses = new HashSet<>();
        learningClasses.add(learningClass);
        student.setLearningClasses(learningClasses);

        studentRepository.create(student);
        learningClassRepository.create(learningClass);
        enrollmentRepository.create(enrollment);

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
        enrollmentRepository.create(enrollment);

        Optional<LearningClass> actualLearningClass = enrollmentRepository.getByLearningClass(UUID.fromString(learningClass.getLearningClassId()));
        assertTrue(actualLearningClass.isPresent());
        assertEquals(learningClass, actualLearningClass.get());
    }
}
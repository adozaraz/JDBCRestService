package org.restservice.repositories;

import org.checkerframework.checker.units.qual.A;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.restservice.entities.Student;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class StudentRepositoryImplTest {
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

    private StudentRepository studentRepository;

    private Student student;

    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        postgres.start();

        student = new Student("Boradulin", "Nikita");

        String url = postgres.getJdbcUrl();
        Properties props = new Properties();
        props.put("user", postgres.getUsername());
        props.put("password", postgres.getPassword());
        conn = DriverManager.getConnection(url, props);
        studentRepository = new StudentRepositoryImpl(conn);
    }

    @AfterEach
    public void after() throws SQLException {
        postgres.close();
        conn.close();
    }

    @Test
    void create() {
        boolean result = studentRepository.create(student);
        assertTrue(result);
    }

    @Test
    void read() {
        studentRepository.create(student);
        Optional<Student> actualStudent = studentRepository.read(UUID.fromString(student.getStudentId()));
        assertTrue(actualStudent.isPresent());
        assertEquals(student, actualStudent.get());
    }

    @Test
    void update() {
        Student changedStudent = new Student(student.getStudentId(), student.getFirstName(), "Lalala");
        studentRepository.create(changedStudent);

        changedStudent.setLastName("Nikita");
        boolean result = studentRepository.update(changedStudent);
        assertTrue(result);

        Optional<Student> actualStudent = studentRepository.read(UUID.fromString(student.getStudentId()));
        assertTrue(actualStudent.isPresent());
        assertEquals(student, actualStudent.get());
    }

    @Test
    void delete() {
        studentRepository.create(student);
        boolean result = studentRepository.delete(student);
        assertTrue(result);
        Optional<Student> actualStudent = studentRepository.read(UUID.fromString(student.getStudentId()));
        assertFalse(actualStudent.isPresent());
    }

    @Test
    void findAll() {
        ArrayList<Student> studentArrayList = new ArrayList<>();
        studentArrayList.add(student);
        studentArrayList.add(new Student("Test", "Name"));
        studentArrayList.add(new Student("Post", "Man"));

        for (Student student1 : studentArrayList) {
            studentRepository.create(student1);
        }

        ArrayList<Student> actualList = (ArrayList<Student>) studentRepository.findAll();
        assertEquals(studentArrayList, actualList);
    }

    @Test
    void findByFirstName() {
        studentRepository.create(student);
        Optional<Student> actualStudent = studentRepository.findByFirstName(student.getFirstName());
        assertTrue(actualStudent.isPresent());
        assertEquals(student, actualStudent.get());
    }

    @Test
    void findByLastName() {
        studentRepository.create(student);
        Optional<Student> actualStudent = studentRepository.findByLastName(student.getLastName());
        assertTrue(actualStudent.isPresent());
        assertEquals(student, actualStudent.get());
    }

    @Test
    void findByFullName() {
        studentRepository.create(student);
        Optional<Student> actualStudent = studentRepository.findByFullName(student.getFirstName(), student.getLastName());
        assertTrue(actualStudent.isPresent());
        assertEquals(student, actualStudent.get());
    }
}
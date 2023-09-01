package org.restservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.repositories.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    StudentService studentService;

    StudentRepository studentRepository;

    EnrollmentService enrollmentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Nikita", "Boradulin");
        this.enrollmentService = mock(EnrollmentService.class);
        this.studentRepository = mock(StudentRepository.class);
        this.studentService = new StudentService(studentRepository, enrollmentService);
    }

    @Test
    void create() {
        when(studentRepository.create(any(Student.class))).thenReturn(true);
        boolean result = studentService.create(student);
        assertTrue(result);
    }

    @Test
    void read() {
        when(studentRepository.read(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));

        Optional<Student> actualResult = studentService.read(UUID.randomUUID());
        assertFalse(actualResult.isPresent());

        actualResult = studentService.read(UUID.fromString(student.getStudentId()));
        assertTrue(actualResult.isPresent());
    }

    @Test
    void update() {
        when(studentRepository.update(student)).thenReturn(true);

        boolean result = studentService.update(new Student());
        assertFalse(result);

        result = studentService.update(student);
        assertTrue(result);
    }

    @Test
    void delete() {
        when(studentRepository.delete(student)).thenReturn(true).thenReturn(false);

        boolean result = studentService.delete(new Student());
        assertFalse(result);

        result = studentService.delete(student);
        assertTrue(result);

        result = studentService.delete(student);
        assertFalse(result);
    }

    @Test
    void findAll() {
        ArrayList<Student> studentArrayList = new ArrayList<>();
        studentArrayList.add(student);
        studentArrayList.add(new Student("Test", "Array"));
        studentArrayList.add(new Student("Boom", "Baam"));

        when(studentRepository.findAll()).thenReturn(studentArrayList);

        ArrayList<Student> result = (ArrayList<Student>) studentService.findAll();

        assertEquals(studentArrayList, result);
    }

    @Test
    void findByFirstName() {
        when(studentRepository.findByFirstName(student.getFirstName())).thenReturn(Optional.of(student));

        Optional<Student> actualResult = studentService.findByFirstName("Lalla");
        assertFalse(actualResult.isPresent());

        actualResult = studentService.findByFirstName(student.getFirstName());
        assertTrue(actualResult.isPresent());
    }

    @Test
    void findByLastName() {
        when(studentRepository.findByLastName(student.getLastName())).thenReturn(Optional.of(student));

        Optional<Student> actualResult = studentService.findByLastName("Lalla");
        assertFalse(actualResult.isPresent());

        actualResult = studentService.findByLastName(student.getLastName());
        assertTrue(actualResult.isPresent());
    }

    @Test
    void findByFullName() {
        when(studentRepository.findByFullName(student.getFirstName(), student.getLastName())).thenReturn(Optional.of(student));

        Optional<Student> actualResult = studentService.findByFullName("Lalla", "Test");
        assertFalse(actualResult.isPresent());

        actualResult = studentService.findByFullName(student.getFirstName(), student.getLastName());
        assertTrue(actualResult.isPresent());
    }

    @Test
    void getStudentWithAttendingClasses() {
        Set<LearningClass> attendingClasses = new HashSet<>();
        attendingClasses.add(new LearningClass("A", "B"));
        attendingClasses.add(new LearningClass("C", "D"));
        attendingClasses.add(new LearningClass("E", "F"));

        student.setLearningClasses(attendingClasses);

        when(enrollmentService.getStudentWithAttendingClasses(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));

        Optional<Student> actualResult = studentService.getStudentWithAttendingClasses(UUID.randomUUID());
        assertFalse(actualResult.isPresent());

        actualResult = studentService.getStudentWithAttendingClasses(UUID.fromString(student.getStudentId()));
        assertTrue(actualResult.isPresent());
    }
}
package org.restservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.restservice.entities.Enrollment;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.repositories.EnrollmentRepository;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EnrollmentServiceTest {

    private EnrollmentService enrollmentService;

    private EnrollmentRepository enrollmentRepository;

    private Student student;

    private LearningClass learningClass;

    @BeforeEach
    public void setUp() {
        student = new Student("Nikita", "Boradulin");
        learningClass = new LearningClass("Test", "Class desc");
        this.enrollmentRepository = Mockito.mock(EnrollmentRepository.class);
        this.enrollmentService = new EnrollmentService(enrollmentRepository);
    }

    @Test
    void create() {
        when(enrollmentRepository.create(any(Enrollment.class))).thenReturn(true);
        boolean result = enrollmentService.create(new Enrollment(student.getStudentId(), learningClass.getLearningClassId()));
        assertTrue(result);
    }

    @Test
    void read() {
        UUID uuid = UUID.randomUUID();
        when(enrollmentRepository.read(uuid)).thenReturn(Optional.of(new Enrollment(student.getStudentId(), learningClass.getLearningClassId())));

        Optional<Enrollment> actualResult = enrollmentService.read(uuid);
        assertTrue(actualResult.isPresent());

        uuid = UUID.randomUUID();
        actualResult = enrollmentService.read(uuid);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void update() {
        Enrollment enrollment = new Enrollment(student.getStudentId(), learningClass.getLearningClassId());
        when(enrollmentRepository.update(enrollment)).thenReturn(true);

        boolean result = enrollmentService.update(enrollment);
        assertTrue(result);
        result = enrollmentService.update(new Enrollment());
        assertFalse(result);
    }

    @Test
    void delete() {
        Enrollment enrollment = new Enrollment(student.getStudentId(), learningClass.getLearningClassId());
        when(enrollmentRepository.delete(enrollment)).thenReturn(true).thenReturn(false);

        boolean result = enrollmentService.delete(new Enrollment());
        assertFalse(result);
        result = enrollmentService.delete(enrollment);
        assertTrue(result);
        result = enrollmentService.delete(enrollment);
        assertFalse(result);
    }

    @Test
    void deleteEnrollmentByStudentId() {
        when(enrollmentRepository.deleteByStudentId(student.getStudentId())).thenReturn(true).thenReturn(false);

        boolean result = enrollmentService.deleteEnrollmentByStudentId(UUID.randomUUID().toString());
        assertFalse(result);
        result = enrollmentService.deleteEnrollmentByStudentId(student.getStudentId());
        assertTrue(result);
        result = enrollmentService.deleteEnrollmentByStudentId(student.getStudentId());
        assertFalse(result);
    }

    @Test
    void deleteEnrollmentByLearningClassId() {
        when(enrollmentRepository.deleteByLearningClassId(learningClass.getLearningClassId())).thenReturn(true).thenReturn(false);

        boolean result = enrollmentService.deleteEnrollmentByLearningClassId(UUID.randomUUID().toString());
        assertFalse(result);
        result = enrollmentService.deleteEnrollmentByLearningClassId(learningClass.getLearningClassId());
        assertTrue(result);
        result = enrollmentService.deleteEnrollmentByLearningClassId(learningClass.getLearningClassId());
        assertFalse(result);
    }

    @Test
    void getStudentWithAttendingClasses() {
        Set<LearningClass> attendingClasses = new HashSet<>();
        attendingClasses.add(learningClass);
        attendingClasses.add(new LearningClass("Aboba", "Baoba"));
        attendingClasses.add(new LearningClass("Baab", "Abba"));

        student.setLearningClasses(attendingClasses);

        when(enrollmentRepository.getByStudent(UUID.fromString(student.getStudentId()))).thenReturn(Optional.of(student));

        Optional<Student> actualResult = enrollmentService.getStudentWithAttendingClasses(UUID.randomUUID());
        assertFalse(actualResult.isPresent());

        actualResult = enrollmentService.getStudentWithAttendingClasses(UUID.fromString(student.getStudentId()));
        assertTrue(actualResult.isPresent());
    }

    @Test
    void getLearningClassWithAttendingStudents() {
        Set<Student> attendingStudents = new HashSet<>();
        attendingStudents.add(student);
        attendingStudents.add(new Student("Test", "Only"));
        attendingStudents.add(new Student("Admin", "LOl"));

        learningClass.setAttendingStudents(attendingStudents);

        when(enrollmentRepository.getByLearningClass(UUID.fromString(learningClass.getLearningClassId()))).thenReturn(Optional.of(learningClass));

        Optional<LearningClass> actualResult = enrollmentService.getLearningClassWithAttendingStudents(UUID.randomUUID());
        assertFalse(actualResult.isPresent());

        actualResult = enrollmentService.getLearningClassWithAttendingStudents(UUID.fromString(learningClass.getLearningClassId()));
        assertTrue(actualResult.isPresent());
    }
}
package org.restservice.services;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.repositories.LearningClassRepository;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.mockito.AdditionalMatchers.not;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class LearningClassServiceTest {

    LearningClassService learningClassService;

    private LearningClassRepository learningClassRepository;

    private EnrollmentService enrollmentService;

    @BeforeEach
    public void setUp() {
        learningClassRepository = mock(LearningClassRepository.class);
        enrollmentService = mock(EnrollmentService.class);
        learningClassService = new LearningClassService(learningClassRepository, enrollmentService);
    }

    @Test
    void create() {
        doReturn(true).when(learningClassRepository).create(any(LearningClass.class));
        boolean result = learningClassService.create(new LearningClass("Test", "description"));
        assertTrue(result);
    }

    @Test
    void read() {
        UUID learningClassId = UUID.randomUUID();
        LearningClass expected = new LearningClass(learningClassId, "Test", "Description");
        when(learningClassRepository.read(learningClassId)).thenReturn(Optional.of(expected));
        Optional<LearningClass> learningClass = learningClassService.read(learningClassId);
        assertTrue(learningClass.isPresent());
        assertEquals(expected, learningClass.get());

        Optional<LearningClass> secondClass = learningClassService.read(UUID.randomUUID());
        assertFalse(secondClass.isPresent());
    }

    @Test
    void update() {
        LearningClass toBeUpdated = new LearningClass("Test", "Blind");
        when(learningClassRepository.update(toBeUpdated)).thenReturn(true);
        boolean result = learningClassService.update(new LearningClass("Test", "Kill"));
        assertFalse(result);

        result = learningClassService.update(toBeUpdated);
        assertTrue(result);
    }

    @Test
    void delete() {
        LearningClass toBeDeleted = new LearningClass("Test", "Test");
        when(learningClassRepository.delete(toBeDeleted)).thenReturn(true).thenReturn(false);

        boolean result = learningClassService.delete(new LearningClass("Test", "Lol"));
        assertFalse(result);

        result = learningClassService.delete(toBeDeleted);
        assertTrue(result);
    }

    @Test
    void getLearningClassWithAttendingStudents() {
        LearningClass toBeFound = new LearningClass("Test", "Body");
        Set<Student> attendingStudents = new HashSet<>();
        attendingStudents.add(new Student("Nikita", "Boradulin"));
        attendingStudents.add(new Student("Nikuta", "Borodulin"));
        attendingStudents.add(new Student("Nikata", "Baradulin"));
        toBeFound.setAttendingStudents(attendingStudents);

        when(enrollmentService.getLearningClassWithAttendingStudents(UUID.fromString(toBeFound.getLearningClassId()))).thenReturn(Optional.of(toBeFound));

        Optional<LearningClass> result = learningClassService.getLearningClassWithAttendingStudents(UUID.randomUUID());
        assertFalse(result.isPresent());

        result = learningClassService.getLearningClassWithAttendingStudents(UUID.fromString(toBeFound.getLearningClassId()));
        assertTrue(result.isPresent());
        assertEquals(toBeFound, result.get());
    }
}
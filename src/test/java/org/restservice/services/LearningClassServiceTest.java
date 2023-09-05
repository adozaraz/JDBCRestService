package org.restservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.repositories.LearningClassRepository;

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

    private LearningClass learningClass;

    private LearningClassDTO learningClassDTO;

    @BeforeEach
    public void setUp() {
        learningClassRepository = mock(LearningClassRepository.class);
        enrollmentService = mock(EnrollmentService.class);
        learningClassService = new LearningClassService(learningClassRepository, enrollmentService);
        learningClassDTO = new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Description");
        learningClass = new LearningClass(learningClassDTO);
    }

    @Test
    void create() {
        doReturn(true).when(learningClassRepository).create(any(LearningClass.class));
        when(learningClassRepository.create(any(LearningClass.class))).thenReturn(true);
        boolean result = learningClassService.create(new LearningClassDTO(UUID.randomUUID().toString(),"Test", "description"));
        assertTrue(result);

        Set<String> students = new HashSet<>();
        students.add(UUID.randomUUID().toString());
        learningClassDTO.setAttendingStudents(students);

        result = learningClassService.create(learningClassDTO);
        assertTrue(result);
        verify(enrollmentService, atLeastOnce()).create(any(String.class), any(String.class));

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
        when(learningClassRepository.update(learningClass)).thenReturn(true);

        boolean result = learningClassService.update(new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Kill"));
        assertFalse(result);

        result = learningClassService.update(learningClassDTO);
        assertTrue(result);
    }

    @Test
    void delete() {
        when(learningClassRepository.delete(learningClass)).thenReturn(true).thenReturn(false);

        boolean result = learningClassService.delete(new LearningClassDTO(UUID.randomUUID().toString(), "Test", "Lol"));
        assertFalse(result);

        result = learningClassService.delete(learningClassDTO);
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
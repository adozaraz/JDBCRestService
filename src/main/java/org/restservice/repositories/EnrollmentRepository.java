package org.restservice.repositories;

import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {
    boolean create(String studentId, String learningClassId);
    boolean deleteByStudentId(String studentId);
    boolean deleteByLearningClassId(String learningClassId);
    Optional<Student> getByStudent(UUID studentId);
    Optional<LearningClass> getByLearningClass(UUID learningClassId);
}

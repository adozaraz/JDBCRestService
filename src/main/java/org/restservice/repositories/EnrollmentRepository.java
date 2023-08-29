package org.restservice.repositories;

import org.restservice.entities.Enrollment;

import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends DAO<Enrollment, UUID> {
    boolean deleteByStudentId(String studentId);
    boolean deleteByLearningClassId(String learningClassId);

    Optional<Enrollment> getByStudent(UUID studentId);

    Optional<Enrollment> getByLearningClass(UUID learningClassId);
}

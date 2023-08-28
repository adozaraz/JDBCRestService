package org.restservice.repositories;

import org.restservice.entities.Enrollment;

import java.util.UUID;

public interface EnrollmentRepository extends DAO<Enrollment, UUID> {
    boolean deleteByStudentId(String studentId);
    boolean deleteByLearningClassId(String learningClassId);
}

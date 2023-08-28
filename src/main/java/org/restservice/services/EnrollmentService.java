package org.restservice.services;

import org.restservice.entities.Enrollment;
import org.restservice.repositories.EnrollmentRepository;

import java.util.Optional;
import java.util.UUID;

public class EnrollmentService {
    private final EnrollmentRepository repository;

    public EnrollmentService(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public boolean createEnrollment(Enrollment enrollment) {
        return this.repository.create(enrollment);
    }

    public Optional<Enrollment> getEnrollment(UUID uuid) {
        return this.repository.read(uuid);
    }

    public boolean updateEnrollment(Enrollment enrollment) {
        return this.repository.update(enrollment);
    }

    public boolean deleteEnrollment(Enrollment enrollment) {
        return this.repository.delete(enrollment);
    }

    public boolean deleteEnrollmentByStudentId(String studentId) { return this.repository.deleteByStudentId(studentId); }

    public boolean deleteEnrollmentByLearningClassId(String learningClassId) { return this.repository.deleteByLearningClassId(learningClassId); }
}

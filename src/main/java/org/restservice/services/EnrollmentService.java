package org.restservice.services;

import org.restservice.entities.Enrollment;
import org.restservice.repositories.EnrollmentRepository;

import java.util.Optional;
import java.util.UUID;

public class EnrollmentService implements Service<Enrollment, UUID> {
    private final EnrollmentRepository repository;

    public EnrollmentService(EnrollmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean create(Enrollment enrollment) {
        return this.repository.create(enrollment);
    }

    @Override
    public Optional<Enrollment> read(UUID uuid) {
        return this.repository.read(uuid);
    }


    @Override
    public boolean update(Enrollment enrollment) {
        return this.repository.update(enrollment);
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        return this.repository.delete(enrollment);
    }

    public boolean deleteEnrollmentByStudentId(String studentId) { return this.repository.deleteByStudentId(studentId); }

    public boolean deleteEnrollmentByLearningClassId(String learningClassId) { return this.repository.deleteByLearningClassId(learningClassId); }
}

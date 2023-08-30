package org.restservice.services;

import org.restservice.entities.LearningClass;
import org.restservice.repositories.LearningClassRepository;
import org.restservice.repositories.LearningClassRepositoryImpl;

import java.sql.Connection;
import java.util.Optional;
import java.util.UUID;

public class LearningClassService implements Service<LearningClass, UUID> {
    private final LearningClassRepository learningClassRepository;
    private final EnrollmentService enrollmentService;

    public LearningClassService(LearningClassRepository learningClassRepository, EnrollmentService enrollmentService) {
        this.learningClassRepository = learningClassRepository;
        this.enrollmentService = enrollmentService;
    }

    public LearningClassService(Connection connection, EnrollmentService enrollmentService) {
        this.learningClassRepository = new LearningClassRepositoryImpl(connection);
        this.enrollmentService = enrollmentService;
    }

    @Override
    public boolean create(LearningClass learningClass) {
        return this.learningClassRepository.create(learningClass);
    }

    @Override
    public Optional<LearningClass> read(UUID uuid) { return this.learningClassRepository.read(uuid); }

    @Override
    public boolean update(LearningClass learningClass) { return this.learningClassRepository.update(learningClass); }

    @Override
    public boolean delete(LearningClass learningClass) {
        if (this.enrollmentService.deleteEnrollmentByLearningClassId(learningClass.getLearningClassId())) return this.learningClassRepository.delete(learningClass);
        else return false;
    }

    public Optional<LearningClass> getLearningClassWithAttendingStudents(UUID learningClassId) { return this.enrollmentService.getLearningClassWithAttendingStudents(learningClassId); }
}

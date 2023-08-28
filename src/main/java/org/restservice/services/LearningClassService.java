package org.restservice.services;

import org.restservice.entities.LearningClass;
import org.restservice.repositories.LearningClassRepository;

import java.util.Optional;
import java.util.UUID;

public class LearningClassService {
    private final LearningClassRepository learningClassRepository;
    private final EnrollmentService enrollmentService;

    public LearningClassService(LearningClassRepository learningClassRepository, EnrollmentService enrollmentService) {
        this.learningClassRepository = learningClassRepository;
        this.enrollmentService = enrollmentService;
    }

    public boolean createLearningClass(LearningClass learningClass) {
        return this.learningClassRepository.create(learningClass);
    }

    public Optional<LearningClass> getLearningClass(UUID uuid) { return this.learningClassRepository.read(uuid); }

    public boolean updateLearningClass(LearningClass learningClass) { return this.learningClassRepository.update(learningClass); }

    public boolean deleteLearningClass(LearningClass learningClass) {
        if (this.enrollmentService.deleteEnrollmentByLearningClassId(learningClass.getLearningClassId())) return this.learningClassRepository.delete(learningClass);
        else return false;
    }
}

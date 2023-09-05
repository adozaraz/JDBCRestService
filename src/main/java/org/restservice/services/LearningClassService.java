package org.restservice.services;

import org.restservice.entities.Enrollment;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.repositories.LearningClassRepository;
import org.restservice.repositories.LearningClassRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class LearningClassService {
    private final LearningClassRepository learningClassRepository;
    private final EnrollmentService enrollmentService;

    public LearningClassService(LearningClassRepository learningClassRepository, EnrollmentService enrollmentService) {
        this.learningClassRepository = learningClassRepository;
        this.enrollmentService = enrollmentService;
    }

    public LearningClassService(EnrollmentService enrollmentService) throws SQLException {
        this.learningClassRepository = new LearningClassRepositoryImpl();
        this.enrollmentService = enrollmentService;
    }

    public boolean create(LearningClassDTO learningClass) {
        if (this.learningClassRepository.create(new LearningClass(learningClass))) {
            if (learningClass.getAttendingStudents() != null) {
                for (String studentId :
                        learningClass.getAttendingStudents()) {
                    this.enrollmentService.create(new Enrollment(studentId, learningClass.getLearningClassId()));
                }
            }
            return true;
        }
        return false;
    }

    public Optional<LearningClass> read(UUID uuid) { return this.learningClassRepository.read(uuid); }

    public boolean update(LearningClassDTO learningClass) { return this.learningClassRepository.update(new LearningClass(learningClass)); }

    public boolean delete(LearningClassDTO learningClass) {
        this.enrollmentService.deleteEnrollmentByLearningClassId(learningClass.getLearningClassId());
        return this.learningClassRepository.delete(new LearningClass(learningClass));
    }

    public Optional<LearningClass> getLearningClassWithAttendingStudents(UUID learningClassId) { return this.enrollmentService.getLearningClassWithAttendingStudents(learningClassId); }
}

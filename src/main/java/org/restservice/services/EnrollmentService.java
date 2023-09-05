package org.restservice.services;

import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;
import org.restservice.repositories.EnrollmentRepository;
import org.restservice.repositories.EnrollmentRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class EnrollmentService {
    private final EnrollmentRepository repository;

    public EnrollmentService(EnrollmentRepository repository) {
        this.repository = repository;
    }

    public EnrollmentService() throws SQLException { this.repository = new EnrollmentRepositoryImpl(); }

    public boolean create(String studentId, String learningClassId) {
        return this.repository.create(studentId, learningClassId);
    }

    public Optional<Student> getStudentWithAttendingClasses(UUID studentId) { return this.repository.getByStudent(studentId); }

    public Optional<LearningClass> getLearningClassWithAttendingStudents(UUID learningClassId) { return this.repository.getByLearningClass(learningClassId); }

    public boolean deleteEnrollmentByStudentId(String studentId) { return this.repository.deleteByStudentId(studentId); }

    public boolean deleteEnrollmentByLearningClassId(String learningClassId) { return this.repository.deleteByLearningClassId(learningClassId); }
}

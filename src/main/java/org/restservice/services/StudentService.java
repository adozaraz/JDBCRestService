package org.restservice.services;

import org.restservice.entities.Student;
import org.restservice.repositories.StudentRepository;
import org.restservice.repositories.StudentRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class StudentService implements Service<Student, UUID> {
    private final StudentRepository studentRepository;

    private final EnrollmentService enrollmentService;

    public StudentService(StudentRepository studentRepository, EnrollmentService enrollmentService) {
        this.studentRepository = studentRepository;
        this.enrollmentService = enrollmentService;
    }

    public StudentService(EnrollmentService enrollmentService) throws SQLException {
        this.studentRepository = new StudentRepositoryImpl();
        this.enrollmentService = enrollmentService;
    }

    @Override
    public boolean create(Student student) { return this.studentRepository.create(student); }

    @Override
    public Optional<Student> read(UUID uuid) { return this.studentRepository.read(uuid); }

    @Override
    public boolean update(Student student) { return this.studentRepository.update(student); }

    @Override
    public boolean delete(Student student) {
        this.enrollmentService.deleteEnrollmentByStudentId(student.getStudentId());
        return this.studentRepository.delete(student);
    }

    public Iterable<Student> findAll() { return this.studentRepository.findAll(); }

    public Optional<Student> findByFirstName(String firstName) { return this.studentRepository.findByFirstName(firstName); }

    public Optional<Student> findByLastName(String lastName) { return this.studentRepository.findByLastName(lastName); }

    public Optional<Student> findByFullName(String firstName, String lastName) { return this.studentRepository.findByFullName(firstName, lastName); }

    public Optional<Student> getStudentWithAttendingClasses(UUID studentId) { return this.enrollmentService.getStudentWithAttendingClasses(studentId); }
}

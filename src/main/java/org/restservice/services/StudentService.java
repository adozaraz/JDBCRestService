package org.restservice.services;

import org.restservice.entities.Enrollment;
import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;
import org.restservice.repositories.StudentRepository;
import org.restservice.repositories.StudentRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class StudentService {
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

    public boolean create(StudentDTO student) {
        if (this.studentRepository.create(new Student(student))) {
            if (student.getLearningClasses() != null) {
                for (String learningClassId :
                        student.getLearningClasses()) {
                    this.enrollmentService.create(new Enrollment(student.getStudentId(), learningClassId));
                }
            }
            return true;
        }
        return false;
    }

    public Optional<Student> read(UUID uuid) { return this.studentRepository.read(uuid); }

    public boolean update(StudentDTO student) { return this.studentRepository.update(new Student(student)); }

    public boolean delete(StudentDTO student) {
        this.enrollmentService.deleteEnrollmentByStudentId(student.getStudentId());
        return this.studentRepository.delete(new Student(student));
    }

    public Iterable<Student> findAll() { return this.studentRepository.findAll(); }

    public Optional<Student> findByFirstName(String firstName) { return this.studentRepository.findByFirstName(firstName); }

    public Optional<Student> findByLastName(String lastName) { return this.studentRepository.findByLastName(lastName); }

    public Optional<Student> findByFullName(String firstName, String lastName) { return this.studentRepository.findByFullName(firstName, lastName); }

    public Optional<Student> getStudentWithAttendingClasses(UUID studentId) { return this.enrollmentService.getStudentWithAttendingClasses(studentId); }
}

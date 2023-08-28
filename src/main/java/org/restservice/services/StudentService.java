package org.restservice.services;

import org.restservice.entities.Student;
import org.restservice.repositories.StudentRepository;

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

    @Override
    public boolean create(Student student) { return this.studentRepository.create(student); }

    @Override
    public Optional<Student> read(UUID uuid) { return this.studentRepository.read(uuid); }

    @Override
    public boolean update(Student student) { return this.studentRepository.update(student); }

    @Override
    public boolean delete(Student student) {
        if (this.enrollmentService.deleteEnrollmentByStudentId(student.getStudentId())) return this.studentRepository.delete(student);
        else return false;
    }

    public Iterable<Student> findAll() { return this.studentRepository.findAll(); }

    public Iterable<Student> findAllById(Iterable<UUID> ids) { return this.studentRepository.findAllById(ids); }

    public Optional<Student> findByFirstName(String firstName) { return this.studentRepository.findByFirstName(firstName); }

    public Optional<Student> findByLastName(String lastName) { return this.studentRepository.findByLastName(lastName); }

    public Optional<Student> findByFullName(String firstName, String lastName) { return this.studentRepository.findByFullName(firstName, lastName); }

    public Iterable<Boolean> saveAll(Iterable<Student> students) {
        ArrayList<Boolean> result = new ArrayList<>();

        for (Student student : students) {
            result.add(this.studentRepository.create(student));
        }

        return result;
    }
}

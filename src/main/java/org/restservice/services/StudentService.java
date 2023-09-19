package org.restservice.services;

import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;
import org.restservice.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> read(UUID uuid) { return this.studentRepository.findById(uuid); }

    public Student update(Student student) { return this.studentRepository.save(student); }

    public void delete(Student student) {
        this.studentRepository.delete(student);
    }

    public Iterable<Student> findAll() { return this.studentRepository.findAll(); }

    public Optional<Student> findByFirstName(String firstName) { return this.studentRepository.findByFirstName(firstName); }

    public Optional<Student> findByLastName(String lastName) { return this.studentRepository.findByLastName(lastName); }

    public Optional<Student> findByFullName(String firstName, String lastName) { return this.studentRepository.findByFullName(firstName, lastName); }
}

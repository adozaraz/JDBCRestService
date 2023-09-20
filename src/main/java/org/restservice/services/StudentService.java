package org.restservice.services;

import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;
import org.restservice.mappers.StudentMapper;
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

    @Autowired
    StudentMapper studentMapper;

    public StudentDTO create(Student student) {
        return studentMapper.studentToStudentDTO(studentRepository.save(student));
    }

    public StudentDTO read(UUID uuid) {
        Optional<Student> result = this.studentRepository.findById(uuid);
        return result.map(student -> studentMapper.studentToStudentDTO(student)).orElse(null);
    }

    public StudentDTO update(Student student) { return studentMapper.studentToStudentDTO(this.studentRepository.save(student)); }

    public void delete(Student student) {
        this.studentRepository.delete(student);
    }

    public Iterable<StudentDTO> findAll() { return studentMapper.mapToStudentDTO(this.studentRepository.findAll()); }

    public StudentDTO findByFirstName(String firstName) {
        Optional<Student> result = this.studentRepository.findByFirstName(firstName);
        return result.map(student -> studentMapper.studentToStudentDTO(student)).orElse(null);
    }

    public StudentDTO findByLastName(String lastName) {
        Optional<Student> result = this.studentRepository.findByLastName(lastName);
        return result.map(student -> studentMapper.studentToStudentDTO(student)).orElse(null);
    }

    public StudentDTO findByFullName(String firstName, String lastName) {
        Optional<Student> result = this.studentRepository.findByFullName(firstName, lastName);
        return result.map(student -> studentMapper.studentToStudentDTO(student)).orElse(null);
    }
}

package org.restservice.repositories;

import org.restservice.entities.Student;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends DAO<Student, UUID> {
    Iterable<Student> findAll();
    Optional<Student> findByFirstName(String firstName);
    Optional<Student> findByLastName(String lastName);
    Optional<Student> findByFullName(String firstName, String lastName);
}

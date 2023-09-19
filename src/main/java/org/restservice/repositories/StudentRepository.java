package org.restservice.repositories;

import org.restservice.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StudentRepository extends CrudRepository<Student, UUID> {
    Iterable<Student> findAll();
    Optional<Student> findByFirstName(String firstName);
    Optional<Student> findByLastName(String lastName);
    Optional<Student> findByFullName(String firstName, String lastName);
}

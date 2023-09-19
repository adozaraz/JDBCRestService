package org.restservice.repositories;

import org.restservice.entities.LearningClass;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LearningClassRepository extends CrudRepository<LearningClass, UUID> {
}

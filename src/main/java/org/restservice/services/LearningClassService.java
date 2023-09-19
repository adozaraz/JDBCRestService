package org.restservice.services;

import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.repositories.LearningClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
public class LearningClassService {

    @Autowired
    private LearningClassRepository learningClassRepository;

    public LearningClass create(LearningClass learningClass) {
        return this.learningClassRepository.save(learningClass);
    }

    public Optional<LearningClass> read(UUID uuid) { return this.learningClassRepository.findById(uuid); }

    public LearningClass update(LearningClass learningClass) { return this.learningClassRepository.save(learningClass); }

    public void delete(LearningClassDTO learningClass) {
        this.learningClassRepository.delete(new LearningClass(learningClass));
    }
}

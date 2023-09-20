package org.restservice.services;

import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;
import org.restservice.entities.Student;
import org.restservice.mappers.LearningClassMapper;
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

    @Autowired
    LearningClassMapper learningClassMapper;

    public LearningClassDTO create(LearningClass learningClass) {
        return learningClassMapper.learningClassToLearningClassDTO(this.learningClassRepository.save(learningClass));
    }

    public LearningClassDTO read(UUID uuid) {
        Optional<LearningClass> learningClass = this.learningClassRepository.findById(uuid);
        return learningClass.map(aClass -> learningClassMapper.learningClassToLearningClassDTO(aClass)).orElse(null);
    }

    public LearningClassDTO update(LearningClass learningClass) { return learningClassMapper.learningClassToLearningClassDTO(this.learningClassRepository.save(learningClass)); }

    public void delete(LearningClass learningClass) {
        this.learningClassRepository.delete(learningClass);
    }
}

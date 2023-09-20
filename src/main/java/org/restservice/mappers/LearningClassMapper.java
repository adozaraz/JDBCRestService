package org.restservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.restservice.entities.LearningClass;
import org.restservice.entities.LearningClassDTO;

@Mapper
public interface LearningClassMapper {
    LearningClassMapper INSTANCE = Mappers.getMapper( LearningClassMapper.class );

    @Mapping(source = "learningClassId", target = "learningClassId")
    @Mapping(source = "attendingStudents", target = "attendingStudents")
    LearningClassDTO learningClassToLearningClassDTO(LearningClass learningClass);
}

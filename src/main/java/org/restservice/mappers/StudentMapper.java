package org.restservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.restservice.entities.Student;
import org.restservice.entities.StudentDTO;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO studentToStudentDTO(Student student);

    Iterable<StudentDTO> mapToStudentDTO(Iterable<Student> students);
}

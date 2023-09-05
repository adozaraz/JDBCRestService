package org.restservice.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Student {
    private UUID studentId;
    private String firstName;
    private String lastName;

    private Set<LearningClass> learningClasses;

    public Student() {
        this.studentId = UUID.randomUUID();
        this.firstName = null;
        this.lastName = null;
        this.learningClasses = null;
    }

    public Student(String firstName, String lastName) {
        this.studentId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.learningClasses = null;
    }

    public Student(UUID id, String firstName, String lastName) {
        this.studentId = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.learningClasses = null;
    }

    public Student(String id, String firstName, String lastName) {
        this.studentId = UUID.fromString(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.learningClasses = null;
    }

    public Student(UUID id, String firstName, String lastName, Set<LearningClass> learningClasses) {
        this.studentId = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.learningClasses = learningClasses;
    }

    public Student(String id, String firstName, String lastName, Set<LearningClass> learningClasses) {
        this.studentId = UUID.fromString(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.learningClasses = learningClasses;
    }

    public Student(StudentDTO studentDTO) {
        this.studentId = UUID.fromString(studentDTO.getStudentId());
        this.firstName = studentDTO.getFirstName();
        this.lastName = studentDTO.getLastName();
        this.learningClasses = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStudentId() {
        return studentId.toString();
    }

    public void setStudentID(UUID id) {
        this.studentId = id;
    }

    public void setStudentID(String id) {
        this.studentId = UUID.fromString(id);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<LearningClass> getLearningClasses() {
        return learningClasses;
    }

    public void setLearningClasses(Set<LearningClass> learningClasses) {
        this.learningClasses = learningClasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(learningClasses, student.learningClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, learningClasses);
    }
}

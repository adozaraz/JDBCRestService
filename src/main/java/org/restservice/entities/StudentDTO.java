package org.restservice.entities;

import java.util.Objects;
import java.util.Set;

public class StudentDTO {
    private String studentId;
    private String firstName;
    private String lastName;

    private Set<String> learningClasses;

    public StudentDTO(String studentId, String firstName, String lastName, Set<String> learningClasses) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.learningClasses = learningClasses;
    }

    public StudentDTO(String studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.learningClasses = null;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getLearningClasses() {
        return learningClasses;
    }

    public void setLearningClasses(Set<String> learningClasses) {
        this.learningClasses = learningClasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(learningClasses, that.learningClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, learningClasses);
    }
}

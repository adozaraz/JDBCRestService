package org.restservice.entities;

import java.util.Objects;
import java.util.UUID;

public class Enrollment {
    private UUID enrollmentId;
    private Student student;
    private LearningClass learningClass;

    public Enrollment() {
        this.enrollmentId = UUID.randomUUID();
        this.student = null;
        this.learningClass = null;
    }

    public Enrollment(Student student, LearningClass learningClass) {
        this.enrollmentId = UUID.randomUUID();
        this.student = student;
        this.learningClass = learningClass;
    }

    public Enrollment(UUID enrollmentId, Student student, LearningClass learningClass) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.learningClass = learningClass;
    }

    public Enrollment(String enrollmentId, Student student, LearningClass learningClass) {
        this.enrollmentId = UUID.fromString(enrollmentId);
        this.student = student;
        this.learningClass = learningClass;
    }

    public String getEnrollmentId() {
        return enrollmentId.toString();
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LearningClass getLearningClass() {
        return learningClass;
    }

    public void setLearningClass(LearningClass learningClass) {
        this.learningClass = learningClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentId, that.enrollmentId) && Objects.equals(student, that.student) && Objects.equals(learningClass, that.learningClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, student, learningClass);
    }
}

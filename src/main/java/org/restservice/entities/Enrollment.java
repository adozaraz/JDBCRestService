package org.restservice.entities;

import java.util.*;

public class Enrollment {
    private UUID enrollmentId;
    private String studentId;
    private String learningClassId;

    public Enrollment() {
        this.enrollmentId = UUID.randomUUID();
        this.studentId = null;
        this.learningClassId = null;
    }

    public Enrollment(String student, String learningClass) {
        this.enrollmentId = UUID.randomUUID();
        this.studentId = student;
        this.learningClassId = learningClass;
    }

    public Enrollment(UUID enrollmentId, String student, String learningClass) {
        this.enrollmentId = enrollmentId;
        this.studentId = student;
        this.learningClassId = learningClass;
    }

    public Enrollment(String enrollmentId, String student, String learningClass) {
        this.enrollmentId = UUID.fromString(enrollmentId);
        this.studentId = student;
        this.learningClassId = learningClass;
    }

    public String getEnrollmentId() {
        return enrollmentId.toString();
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudent(String student) {
        this.studentId = student;
    }

    public String getLearningClassId() {
        return learningClassId;
    }

    public void setLearningClass(String learningClass) {
        this.learningClassId = learningClass;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentId, that.enrollmentId) && Objects.equals(studentId, that.studentId) && Objects.equals(learningClassId, that.learningClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, studentId, learningClassId);
    }
}

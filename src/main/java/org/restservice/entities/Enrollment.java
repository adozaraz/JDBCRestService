package org.restservice.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Enrollment {
    private UUID enrollmentId;
    private List<Student> students;
    private List<LearningClass> learningClasses;

    public Enrollment() {
        this.enrollmentId = UUID.randomUUID();
        this.students = null;
        this.learningClasses = null;
    }

    public Enrollment(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
        this.students = new ArrayList<>();
        this.learningClasses = new ArrayList<>();
    }

    public Enrollment(String enrollmentId) {
        this.enrollmentId = UUID.fromString(enrollmentId);
        this.students = new ArrayList<>();
        this.learningClasses = new ArrayList<>();
    }

    public Enrollment(List<Student> students, List<LearningClass> learningClasses) {
        this.enrollmentId = UUID.randomUUID();
        this.students = students;
        this.learningClasses = learningClasses;
    }

    public Enrollment(UUID enrollmentId, List<Student> students, List<LearningClass> learningClasses) {
        this.enrollmentId = enrollmentId;
        this.students = students;
        this.learningClasses = learningClasses;
    }

    public Enrollment(String enrollmentId, List<Student> students, List<LearningClass> learningClasses) {
        this.enrollmentId = UUID.fromString(enrollmentId);
        this.students = students;
        this.learningClasses = learningClasses;
    }

    public String getEnrollmentId() {
        return enrollmentId.toString();
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) { this.students.add(student); }

    public Student getStudent(int index) { return this.students.get(index); }

    public List<LearningClass> getLearningClasses() {
        return learningClasses;
    }

    public void setLearningClasses(List<LearningClass> learningClasses) {
        this.learningClasses = learningClasses;
    }

    public void addLearningClass(LearningClass learningClass) { this.learningClasses.add(learningClass); }

    public LearningClass getLearningClass(int index) { return this.learningClasses.get(index); }

    public int getStudentsSize() { return students.size(); }

    public int getLearningClassesSize() { return learningClasses.size(); }

    public boolean contains(Student student) { return this.students.contains(student); }

    public boolean contains(LearningClass learningClass) { return this.learningClasses.contains(learningClass); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentId, that.enrollmentId) && Objects.equals(students, that.students) && Objects.equals(learningClasses, that.learningClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, students, learningClasses);
    }
}

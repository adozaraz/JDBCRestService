package org.restservice.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class LearningClass {
    private UUID learningClassId;
    private String title;
    private String description;

    private Set<Student> attendingStudents;

    public LearningClass() {
        this.learningClassId = UUID.randomUUID();
        this.title = null;
        this.description = null;
        this.attendingStudents = null;
    }

    public LearningClass(String title, String description) {
        this.learningClassId = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.attendingStudents = null;
    }

    public LearningClass(UUID learningClassId, String title, String description) {
        this.learningClassId = learningClassId;
        this.title = title;
        this.description = description;
        this.attendingStudents = null;
    }

    public LearningClass(String learningClassId, String title, String description) {
        this.learningClassId = UUID.fromString(learningClassId);
        this.title = title;
        this.description = description;
        this.attendingStudents = null;
    }

    public LearningClass(UUID learningClassId, String title, String description, Set<Student> attendingStudents) {
        this.learningClassId = learningClassId;
        this.title = title;
        this.description = description;
        this.attendingStudents = attendingStudents;
    }

    public LearningClass(String learningClassId, String title, String description, Set<Student> attendingStudents) {
        this.learningClassId = UUID.fromString(learningClassId);
        this.title = title;
        this.description = description;
        this.attendingStudents = attendingStudents;
    }


    public String getLearningClassId() {
        return learningClassId.toString();
    }

    public void setLearningClassId(UUID learningClassId) {
        this.learningClassId = learningClassId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Student> getAttendingStudents() {
        return attendingStudents;
    }

    public void setAttendingStudents(Set<Student> attendingStudents) {
        this.attendingStudents = attendingStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningClass that = (LearningClass) o;
        return Objects.equals(learningClassId, that.learningClassId) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(attendingStudents, that.attendingStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(learningClassId, title, description, attendingStudents);
    }
}

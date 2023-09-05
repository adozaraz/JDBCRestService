package org.restservice.entities;

import java.util.Objects;
import java.util.Set;

public class LearningClassDTO {
    private String learningClassId;
    private String title;
    private String description;

    private Set<String> attendingStudents;

    public LearningClassDTO(String learningClassId, String title, String description, Set<String> attendingStudents) {
        this.learningClassId = learningClassId;
        this.title = title;
        this.description = description;
        this.attendingStudents = attendingStudents;
    }

    public LearningClassDTO(String learningClassId, String title, String description) {
        this.learningClassId = learningClassId;
        this.title = title;
        this.description = description;
        this.attendingStudents = null;
    }

    public String getLearningClassId() {
        return learningClassId;
    }

    public void setLearningClassId(String learningClassId) {
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


    public Set<String> getAttendingStudents() {
        return attendingStudents;
    }

    public void setAttendingStudents(Set<String> attendingStudents) {
        this.attendingStudents = attendingStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningClassDTO that = (LearningClassDTO) o;
        return Objects.equals(learningClassId, that.learningClassId) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(attendingStudents, that.attendingStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(learningClassId, title, description, attendingStudents);
    }
}

package org.restservice.entities;

import java.util.Objects;
import java.util.UUID;

public class LearningClass {
    private UUID learningClassId;
    private String title;
    private String description;

    public void setLearningClassId() {
        this.learningClassId = UUID.randomUUID();
        this.title = null;
        this.description = null;
    }

    public LearningClass(UUID learningClassId, String title, String description) {
        this.learningClassId = learningClassId;
        this.title = title;
        this.description = description;
    }

    public LearningClass(String learningClassId, String title, String description) {
        this.learningClassId = UUID.fromString(learningClassId);
        this.title = title;
        this.description = description;
    }


    public UUID getLearningClassId() {
        return learningClassId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningClass that = (LearningClass) o;
        return Objects.equals(learningClassId, that.learningClassId) && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(learningClassId, title, description);
    }
}

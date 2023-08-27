package org.restservice.entities;

import java.util.Objects;
import java.util.UUID;

public class Student {
    private UUID studentId;
    private String firstName;
    private String lastName;

    public Student() {
        this.studentId = UUID.randomUUID();
        this.lastName = null;
    }

    public Student(String firstName, String lastName) {
        this.studentId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(UUID id, String firstName, String lastName) {
        this.studentId = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Student(String id, String firstName, String lastName) {
        this.studentId = UUID.fromString(id);
        this.lastName = lastName;
        this.firstName = firstName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(lastName, student.lastName) && Objects.equals(firstName, student.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, lastName, firstName);
    }
}

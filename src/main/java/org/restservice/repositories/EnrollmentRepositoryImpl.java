package org.restservice.repositories;

import org.restservice.DbConnection;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private DbConnection connection;

    private enum SQLUser {
        GET("SELECT e.enrollmentId, s.studentId, l.learningClassId FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.enrollmentId = (?)"),
        GET_BY_STUDENT("SELECT s.studentId, s.firstName, s.lastName, l.learningClassId, l.title, l.description FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.student = (?)"),
        GET_BY_LEARNING_CLASS("SELECT s.studentId, s.firstName, s.lastName, l.learningClassId, l.title, l.description FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.learningClass = (?)"),
        INSERT("INSERT INTO enrollments (enrollmentId, student, learningClass) VALUES ((?), (?), (?)) RETURNING enrollmentId"),
        UPDATE("UPDATE enrollments SET student = (?), learningClass = (?) WHERE enrollmentId = (?) RETURNING enrollmentId"),
        DELETE("DELETE FROM enrollments WHERE enrollmentId = (?) AND learningClass = (?) AND student = (?) RETURNING enrollmentId"),
        DELETE_BY_STUDENT_ID("DELETE FROM enrollments WHERE student = (?) RETURNING enrollmentId"),
        DELETE_BY_LEARNING_CLASS_ID("DELETE FROM enrollments WHERE learningClass = (?) RETURNING enrollmentId");

        final String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    public EnrollmentRepositoryImpl() throws SQLException {
        this.connection = DbConnection.getInstance();
    }
    @Override
    public boolean create(String studentId, String learningClassId) {
        boolean result = false;
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, studentId);
            statement.setString(3, learningClassId);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteByStudentId(String studentId) {
        boolean result = false;
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.DELETE_BY_STUDENT_ID.QUERY)) {
            statement.setString(1, studentId);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteByLearningClassId(String learningClassId) {
        boolean result = false;
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.DELETE_BY_LEARNING_CLASS_ID.QUERY)) {
            statement.setString(1, learningClassId);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Student> getByStudent(UUID studentId) {
        Optional<Student> result = Optional.empty();
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.GET_BY_STUDENT.QUERY)) {
            statement.setString(1, studentId.toString());
            final ResultSet rs = statement.executeQuery();
            Set<LearningClass> learningClasses = new HashSet<>();
            Student student = null;
            while (rs.next()) {
                if (student == null) {
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    student = new Student(studentId, firstName, lastName);
                }
                String learningClassId = rs.getString("learningClassId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                learningClasses.add(new LearningClass(learningClassId, title, description));
            }
            if (student != null && !learningClasses.isEmpty()) {
                student.setLearningClasses(learningClasses);
                result = Optional.of(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<LearningClass> getByLearningClass(UUID learningClassId) {
        Optional<LearningClass> result = Optional.empty();
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.GET_BY_LEARNING_CLASS.QUERY)) {
            statement.setString(1, learningClassId.toString());
            final ResultSet rs = statement.executeQuery();
            Set<Student> attendingStudents = new HashSet<>();
            LearningClass learningClass = null;
            while (rs.next()) {
                if (learningClass == null) {
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    learningClass = new LearningClass(learningClassId, title, description);
                }
                String studentId = rs.getString("studentId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                attendingStudents.add(new Student(studentId, firstName, lastName));
            }
            if (learningClass != null && !attendingStudents.isEmpty()) {
                learningClass.setAttendingStudents(attendingStudents);
                result = Optional.of(learningClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

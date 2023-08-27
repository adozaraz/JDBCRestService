package org.restservice.repositories;

import org.restservice.entities.Enrollment;
import org.restservice.entities.LearningClass;
import org.restservice.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    final Connection connection;

    private enum SQLUser {
        GET("SELECT e.enrollmentId, s.studentId, s.firstName, s.lastName, l.learningClassId, l.title, l.description FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.enrollmentId = (?)"),
        INSERT("INSERT INTO enrollments (enrollmentId, student, learningClass) VALUES ((?), (?), (?)) RETURNING enrollmentId"),
        UPDATE("UPDATE enrollments SET student = (?), learningClass = (?) WHERE enrollmentId = (?) RETURNING enrollmentId"),
        DELETE("DELETE FROM enrollments WHERE enrollmentId = (?) AND learningClass = (?) AND student = (?) RETURNING enrollmentId");

        final String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    public EnrollmentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Enrollment enrollment) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, enrollment.getEnrollmentId());
            statement.setString(2, enrollment.getStudent().getStudentId());
            statement.setString(3, enrollment.getLearningClass().getLearningClassId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Enrollment> read(UUID uuid) {
        Optional<Enrollment> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, uuid.toString());
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String studentId = rs.getString("studentId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String learningClassId = rs.getString("learningClassId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String enrollmentId = rs.getString("enrollmentId");
                result = Optional.of(new Enrollment(enrollmentId, new Student(studentId, firstName, lastName), new LearningClass(learningClassId, title, description)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Enrollment enrollment) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, enrollment.getStudent().getStudentId());
            statement.setString(2, enrollment.getLearningClass().getLearningClassId());
            statement.setString(3, enrollment.getEnrollmentId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setString(1, enrollment.getEnrollmentId());
            statement.setString(2, enrollment.getStudent().getStudentId());
            statement.setString(3, enrollment.getLearningClass().getLearningClassId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

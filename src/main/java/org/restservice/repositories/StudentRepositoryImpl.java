package org.restservice.repositories;

import org.restservice.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentRepositoryImpl implements StudentRepository {

    private enum SQLUser {
        GET("SELECT u.studentId, u.firstName, u.lastName FROM students AS u WHERE u.studentId = (?)"),
        GET_ALL("SELECT * FROM students"),
        GET_BY_FIRST("SELECT * FROM students where firstName = (?)"),
        GET_BY_LAST("SELECT * FROM students where lastName = (?)"),
        GET_BY_FIRST_AND_LAST("SELECT * FROM students where firstName = (?) AND lastName = (?)"),
        INSERT("INSERT INTO students (studentId, firstName, lastName) VALUES ((?), (?), (?)) RETURNING studentId"),
        UPDATE("UPDATE students SET firstName = (?), lastName = (?) WHERE studentId = (?) RETURNING studentId"),
        DELETE("DELETE FROM students WHERE studentId = (?) AND firstName = (?) AND lastName = (?) RETURNING studentId");

        final String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    final Connection connection;

    public StudentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Student student) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());

            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Student> read(UUID uuid) {
        Optional<Student> result = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, uuid.toString());

            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                result = Optional.of(new Student(uuid, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Student student) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());

            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean delete(Student student) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());

            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Iterable<Student> findAll() {
        ArrayList<Student> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_ALL.QUERY)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final String id = rs.getString("studentId");
                final String firstName = rs.getString("firstName");
                final String lastName = rs.getString("lastName");
                result.add(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterable<Student> findAllById(Iterable<UUID> ids) {
        ArrayList<Student> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            for (UUID id : ids) {
                statement.setString(1, id.toString());
                final ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    result.add(new Student(id, firstName, lastName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Student> findByFirstName(String firstName) {
        Optional<Student> result = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_BY_FIRST.QUERY)) {
            statement.setString(1, firstName);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String id = rs.getString("studentId");
                String lastName = rs.getString("lastName");
                result = Optional.of(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Student> findByLastName(String lastName) {
        Optional<Student> result = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_BY_LAST.QUERY)) {
            statement.setString(1, lastName);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String id = rs.getString("studentId");
                String firstName = rs.getString("firstName");
                result = Optional.of(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Student> findByFullName(String firstName, String lastName) {
        Optional<Student> result = Optional.empty();

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_BY_FIRST_AND_LAST.QUERY)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String id = rs.getString("studentId");
                result = Optional.of(new Student(id, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}

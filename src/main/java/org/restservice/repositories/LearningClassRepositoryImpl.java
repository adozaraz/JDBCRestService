package org.restservice.repositories;

import org.restservice.DbConnection;
import org.restservice.entities.LearningClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class LearningClassRepositoryImpl implements LearningClassRepository {

    private DbConnection connection;

    private enum SQLUser {
        GET("SELECT learningClassId, title, description FROM learningCLasses WHERE learningClassId = (?)"),
        INSERT("INSERT INTO learningClasses (learningClassId, title, description) VALUES ((?), (?), (?)) RETURNING learningClassId"),
        UPDATE("UPDATE learningClasses SET title = (?), description = (?) WHERE learningClassId = (?) RETURNING learningClassId"),
        DELETE("DELETE FROM learningClasses WHERE learningClassId = (?) AND title = (?) AND description = (?) RETURNING learningClassId");

        final String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    public LearningClassRepositoryImpl() throws SQLException {
        this.connection = DbConnection.getInstance();
    }

    @Override
    public boolean create(LearningClass learningClass) {
        boolean result = false;
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, learningClass.getLearningClassId());
            statement.setString(2, learningClass.getTitle());
            statement.setString(3, learningClass.getDescription());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<LearningClass> read(UUID uuid) {
        Optional<LearningClass> result = Optional.empty();
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, uuid.toString());
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                result = Optional.of(new LearningClass(uuid, title, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(LearningClass learningClass) {
        boolean result = false;
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, learningClass.getTitle());
            statement.setString(2, learningClass.getDescription());
            statement.setString(3, learningClass.getLearningClassId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(LearningClass learningClass) {
        boolean result = false;
        try (PreparedStatement statement = connection.getPreparedStatement(SQLUser.DELETE.QUERY)) {
            statement.setString(1, learningClass.getLearningClassId());
            statement.setString(2, learningClass.getTitle());
            statement.setString(3, learningClass.getDescription());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

package org.restservice.repositories;

import org.restservice.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepositoryImplementation implements UserRepository {

    final Connection connection;

    public UserRepositoryImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, user.getUUID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public User read(UUID uuid) {
        final User result = new User();
        result.setUUID(UUID.fromString("-1"));
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, uuid.toString());
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setUUID(uuid);
                result.setUsername(rs.getString("username"));
                result.setPassword(rs.getString("password"));
                result.setRole(new User.Role(rs.getString("roleId"), rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getUUID());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean delete(User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setString(1, user.getUUID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean deleteByKey(UUID uuid) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<UUID> ids) {
        return null;
    }

    @Override
    public boolean saveAll(Iterable<User> users) {
        return false;
    }

    enum SQLUser {
        GET("SELECT u.id, u.username, u.password, u.role FROM users AS u LEFT JOIN roles AS r ON u.role = r WHERE u.username = (?)"),
        INSERT("INSERT INTO users (id, username, password, role) VALUES ((?), (?), (?), (?)) RETURNING id"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id"),
        DELETE("DELETE FROM users WHERE id = (?) AND username = (?) AND password = (?) RETURNING id");

        final String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

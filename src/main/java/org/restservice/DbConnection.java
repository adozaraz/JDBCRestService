package org.restservice;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DbConnection {
    private static DbConnection INSTANCE;

    private Connection connection;

    private Properties props;

    private String fileProperties;
    private DbConnection(Connection connection) {
        this.connection = connection;
    }

    private DbConnection(String fileProperties) throws SQLException {
        this.fileProperties = fileProperties;
        try (InputStream inputStream = new FileInputStream(fileProperties)) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectToServer();
    }

    public static DbConnection getInstance() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new DbConnection();
        }
        return INSTANCE;
    }

    public void connectToServer() throws SQLException {
        connection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void saveProperties() {
        try (OutputStream outputStream = new FileOutputStream(fileProperties)) {
            props.store(outputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DbConnection() throws SQLException {
        this("application.properties");
    }

    public void changeUsernameProperty(String username) throws SQLException {
        closeConnection();
        props.setProperty("db.user", username);
        saveProperties();
        connectToServer();
    }

    public void changePasswordProperty(String password) throws SQLException {
        closeConnection();
        props.setProperty("db.password", password);
        saveProperties();
        connectToServer();
    }

    public void changeURL(String url) throws SQLException {
        closeConnection();
        props.setProperty("db.url", url);
        saveProperties();
        connectToServer();
    }

    public PreparedStatement getPreparedStatement(String sqlQuery) throws SQLException {
        return connection.prepareStatement(sqlQuery);
    }

    public void changeProps(Properties props) throws SQLException {
        closeConnection();
        this.props = props;
        saveProperties();
        connectToServer();
    }
}

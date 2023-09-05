package org.restservice;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbConnectionTest {
    @Test
    void getInstance() throws SQLException {
        DbConnection connection1 = DbConnection.getInstance();
        DbConnection connection2 = DbConnection.getInstance();
        assertEquals(connection1, connection2);
    }

    @Test
    void scanFileForProperties() throws SQLException {
        DbConnection connection = DbConnection.getInstance();
        Properties expectedProperties = new Properties();
        expectedProperties.setProperty("db.user", "adozaraz");
        expectedProperties.setProperty("db.password", "12345");
        expectedProperties.setProperty("db.url", "jdbc:postgresql://localhost/test");
        expectedProperties.setProperty("db.database", "TEST_DATABASE");
        Properties actualProperties = connection.getProps();
        assertEquals(expectedProperties, actualProperties);
    }
}

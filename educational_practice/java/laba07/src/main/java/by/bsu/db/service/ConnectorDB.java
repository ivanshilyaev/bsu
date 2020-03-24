package by.bsu.db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/conference_db";
        String login = "application";
        String password = "application_password";
        return DriverManager.getConnection(url, login, password);
    }
}

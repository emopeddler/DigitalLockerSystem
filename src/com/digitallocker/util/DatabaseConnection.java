package com.digitallocker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/digitallocker";
        String user = "root"; // your MySQL username
        String password = "AS@25528227"; //

        return DriverManager.getConnection(url, user, password);
    }
}
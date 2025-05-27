package com.digitallocker.dao;

import com.digitallocker.util.DatabaseConnection;
import java.sql.*;

public class UserDao {

    public static boolean registerUser(String username, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsInserted = stmt.executeUpdate();

            stmt.close();
            conn.close();

            return rowsInserted > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean validateLogin(String username, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            boolean success = rs.next();

            rs.close();
            stmt.close();
            conn.close();

            return success;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // NEW: Change password
    public static boolean changePassword(String username, String oldPassword, String newPassword) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            // Check if old credentials match
            String checkSql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            checkStmt.setString(2, oldPassword);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Old password is correct, proceed to update
                String updateSql = "UPDATE users SET password = ? WHERE username = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);

                int rowsUpdated = updateStmt.executeUpdate();

                updateStmt.close();
                rs.close();
                checkStmt.close();
                conn.close();

                return rowsUpdated > 0;
            } else {
                rs.close();
                checkStmt.close();
                conn.close();
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
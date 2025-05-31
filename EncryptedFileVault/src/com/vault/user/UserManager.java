package com.vault.user;

import java.sql.*;
import java.util.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.security.MessageDigest;

public class UserManager {
    private static final String DB_URL = "jdbc:sqlite:vault/vault.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()){

            String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password_hash TEXT NOT NULL,
                    salt TEXT NOT NULL,
                    vault_path TEXT NOT NULL
                );
            """;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error initializing DB: " + e.getMessage());
        }
    }

    public static User register(Scanner scan) {
        System.out.println("Please enter a username: ");
        String username = scan.nextLine();

        System.out.println("Please enter a password: ");
        String password = scan.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(password, salt);
            String vaultPath = "vault/" + username;

            String sql = "INSERT INTO users (username, password_hash, salt, vault_path) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstmt.setString(1, username);
                pstmt.setString(2, hashedPassword);
                pstmt.setString(3, salt);
                pstmt.setString(4, vaultPath);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    return new User(userId, username, vaultPath);
                }
            } catch (SQLException e) {
                System.out.println("Registration Error: " + e.getMessage());
            }

            return null;
        } catch (SQLException e) {
            System.out.println("Registration Connection Error: " + e.getMessage());
        }

        return null;
    }

    public static User login(Scanner scan) {
        System.out.println("Please enter your username: ");
        String username = scan.nextLine();

        System.out.println("Please enter your password: ");
        String password = scan.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String salt = rs.getString("salt");
                    String hashedPass = rs.getString("password_hash");

                    if (hashPassword(password, salt).equals(hashedPass)) {
                        int id = rs.getInt("id");
                        String vaultPath = rs.getString("vault_path");
                        return new User(id, username, vaultPath);
                    } else {
                        System.out.println("Wrong password");
                    }
                } else {
                    System.out.println("User not found");
                }
            }
        } catch (SQLException e) {
            System.out.println("Login Connection Error: " + e.getMessage());
        }

        return null;
    }

    private static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

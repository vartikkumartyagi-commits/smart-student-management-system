package com.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {

        try {

            // Railway MySQL configuration
            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String database = System.getenv("MYSQLDATABASE");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");

            // Local MySQL fallback
            if (host == null || host.isEmpty()) {

                host = "localhost";
                port = "3307";
                database = "student_management";
                user = "root";
                password = System.getenv("DB_PASSWORD");
            }

            if (password == null || password.isEmpty()) {

                System.out.println("Database password is not configured.");
                return null;
            }

            String url =
                    "jdbc:mysql://" + host + ":" + port + "/" + database
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            Connection connection =
                    DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully!");

            return connection;

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();

            return null;
        }
    }
}
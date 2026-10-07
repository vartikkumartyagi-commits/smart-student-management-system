package com.student;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Smart Student Management System ===");

        // Test MySQL connection
        DatabaseConnection.getConnection();

        System.out.println("System is ready!");
        System.out.println("Students will be added through the website.");
    }
}
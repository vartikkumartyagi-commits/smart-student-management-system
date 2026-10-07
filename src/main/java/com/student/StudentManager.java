package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {

        students.add(student);

        String sql = "INSERT INTO students " +
                "(name, course, marks, email, phone, semester, section, date_of_birth, gender, attendance, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getCourse());
            statement.setDouble(3, student.getMarks());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getPhone());
            statement.setInt(6, student.getSemester());
            statement.setString(7, student.getSection());
            statement.setString(8, student.getDateOfBirth());
            statement.setString(9, student.getGender());
            statement.setDouble(10, student.getAttendance());
            statement.setString(11, student.getAddress());

            statement.executeUpdate();

            System.out.println("Student saved to database successfully!");

        } catch (SQLException e) {

            System.out.println("Error saving student to database!");
            e.printStackTrace();
        }
    }

    public void showStudents() {

        for (Student student : students) {

            System.out.println(
                "ID: " + student.getId() +
                ", Name: " + student.getName() +
                ", Course: " + student.getCourse() +
                ", Marks: " + student.getMarks() +
                ", Email: " + student.getEmail() +
                ", Phone: " + student.getPhone() +
                ", Semester: " + student.getSemester() +
                ", Section: " + student.getSection() +
                ", DOB: " + student.getDateOfBirth() +
                ", Gender: " + student.getGender() +
                ", Attendance: " + student.getAttendance() +
                ", Address: " + student.getAddress()
            );
        }
    }
}
package com.student;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * StudentServer
 *
 * Main web server for the Smart Student Management System.
 *
 * Responsibilities:
 * 1. Start the HTTP server.
 * 2. Handle student management requests.
 * 3. Connect web pages with the MySQL database.
 * 4. Perform CRUD operations.
 * 5. Provide dashboard statistics.
 */
public class StudentServer {

    // Server port
    private static final int PORT = 8080;

    // Folder containing HTML/CSS files
    private static final String WEB_FOLDER =
            "src/main/java/com/student/web";


    /**
     * Main method.
     *
     * Starts the HTTP server and registers all application routes.
     */
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(PORT),
                0
        );

        // =========================
        // STATIC WEB PAGES
        // =========================

        server.createContext("/", StudentServer::handleHome);

        server.createContext(
                "/add-student.html",
                StudentServer::handleAddStudentPage
        );

        server.createContext(
                "/students.html",
                StudentServer::handleStudentsPage
        );

        server.createContext(
                "/search-student.html",
                StudentServer::handleSearchStudentPage
        );

        server.createContext(
                "/update-student.html",
                StudentServer::handleUpdateStudentPage
        );

        server.createContext(
                "/delete-student.html",
                StudentServer::handleDeleteStudentPage
        );

        server.createContext(
                "/style.css",
                StudentServer::handleStyle
        );


        // =========================
        // STUDENT OPERATIONS
        // =========================

        server.createContext(
                "/add-student",
                StudentServer::handleAddStudent
        );

        server.createContext(
                "/students",
                StudentServer::handleStudents
        );

        server.createContext(
                "/search-student",
                StudentServer::handleSearchStudent
        );

        server.createContext(
                "/update-student",
                StudentServer::handleUpdateStudent
        );

        server.createContext(
                "/delete-student",
                StudentServer::handleDeleteStudent
        );


        // =========================
        // DASHBOARD STATISTICS
        // =========================

        server.createContext(
                "/statistics",
                StudentServer::handleStatistics
        );


        // Start server
        server.start();


        System.out.println();
        System.out.println("======================================");
        System.out.println("Smart Student Management System");
        System.out.println("Server started successfully!");
        System.out.println("Open: http://localhost:8080");
        System.out.println("======================================");
        System.out.println();
    }


    // =========================================================
    // HOME PAGE
    // =========================================================

    private static void handleHome(HttpExchange exchange)
            throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            sendResponse(
                    exchange,
                    405,
                    "Method Not Allowed"
            );
            return;
        }

        sendFile(
                exchange,
                WEB_FOLDER + "/index.html",
                "text/html"
        );
    }


    // =========================================================
    // ADD STUDENT PAGE
    // =========================================================

    private static void handleAddStudentPage(
            HttpExchange exchange
    ) throws IOException {

        sendFile(
                exchange,
                WEB_FOLDER + "/add-student.html",
                "text/html"
        );
    }


    // =========================================================
    // VIEW STUDENTS PAGE
    // =========================================================

    private static void handleStudentsPage(
            HttpExchange exchange
    ) throws IOException {

        sendFile(
                exchange,
                WEB_FOLDER + "/students.html",
                "text/html"
        );
    }


    // =========================================================
    // SEARCH PAGE
    // =========================================================

    private static void handleSearchStudentPage(
            HttpExchange exchange
    ) throws IOException {

        sendFile(
                exchange,
                WEB_FOLDER + "/search-student.html",
                "text/html"
        );
    }


    // =========================================================
    // UPDATE PAGE
    // =========================================================

    private static void handleUpdateStudentPage(
            HttpExchange exchange
    ) throws IOException {

        sendFile(
                exchange,
                WEB_FOLDER + "/update-student.html",
                "text/html"
        );
    }


    // =========================================================
    // DELETE PAGE
    // =========================================================

    private static void handleDeleteStudentPage(
            HttpExchange exchange
    ) throws IOException {

        sendFile(
                exchange,
                WEB_FOLDER + "/delete-student.html",
                "text/html"
        );
    }


    // =========================================================
    // CSS FILE
    // =========================================================

    private static void handleStyle(
            HttpExchange exchange
    ) throws IOException {

        sendFile(
                exchange,
                WEB_FOLDER + "/style.css",
                "text/css"
        );
    }


    // =========================================================
    // ADD STUDENT
    // =========================================================

    private static void handleAddStudent(
            HttpExchange exchange
    ) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            sendResponse(
                    exchange,
                    405,
                    "Method Not Allowed"
            );

            return;
        }


        try {

            String formData = readRequestBody(exchange);

            Map<String, String> data =
                    parseFormData(formData);


            String name =
                    safeValue(data.get("name"));

            String course =
                    safeValue(data.get("course"));

            double marks =
                    Double.parseDouble(
                            safeValue(data.get("marks"))
                    );

            String email =
                    safeValue(data.get("email"));

            String phone =
                    safeValue(data.get("phone"));

            int semester =
                    Integer.parseInt(
                            safeValue(data.get("semester"))
                    );

            String section =
                    safeValue(data.get("section"));

            String dateOfBirth =
                    safeValue(data.get("date_of_birth"));

            String gender =
                    safeValue(data.get("gender"));

            double attendance =
                    Double.parseDouble(
                            safeValue(data.get("attendance"))
                    );

            String address =
                    safeValue(data.get("address"));


            String sql =
                    "INSERT INTO students " +
                    "(name, course, marks, email, phone, " +
                    "semester, section, date_of_birth, gender, " +
                    "attendance, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


            try (Connection connection =
                         DatabaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {


                statement.setString(1, name);
                statement.setString(2, course);
                statement.setDouble(3, marks);
                statement.setString(4, email);
                statement.setString(5, phone);
                statement.setInt(6, semester);
                statement.setString(7, section);
                statement.setDate(
                        8,
                        Date.valueOf(dateOfBirth)
                );
                statement.setString(9, gender);
                statement.setDouble(10, attendance);
                statement.setString(11, address);


                statement.executeUpdate();
            }


            sendResponse(
                    exchange,
                    200,
                    successPage(
                            "Student Added Successfully",
                            "The student record has been saved to the database."
                    )
            );


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    500,
                    errorPage(
                            "Unable to Add Student",
                            e.getMessage()
                    )
            );
        }
    }


    // =========================================================
    // VIEW ALL STUDENTS
    // =========================================================

    private static void handleStudents(
            HttpExchange exchange
    ) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            sendResponse(
                    exchange,
                    405,
                    "Method Not Allowed"
            );

            return;
        }


        StringBuilder html =
                new StringBuilder();


        html.append("""
                <!DOCTYPE html>
                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">

                    <title>All Students</title>

                    <link rel="stylesheet"
                          href="/style.css">

                </head>

                <body>

                <header class="hero">

                    <div class="hero-content">

                        <div class="brand-icon">
                            🎓
                        </div>

                        <div>

                            <div class="brand-name">
                                StudentHub
                            </div>

                            <div class="brand-subtitle">
                                Smart Student Management System
                            </div>

                        </div>

                    </div>

                </header>

                <main>

                <div class="students-page">

                    <div class="top-header">

                        <div>

                            <p class="small-heading">
                                DATABASE RECORDS
                            </p>

                            <h1 class="page-title">
                                All Students
                            </h1>

                        </div>

                        <a href="/add-student.html"
                           class="add-btn">
                            + Add Student
                        </a>

                    </div>

                    <div class="table-container">

                    <table>

                    <thead>

                    <tr>

                        <th>ID</th>
                        <th>Name</th>
                        <th>Course</th>
                        <th>Marks</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Semester</th>
                        <th>Section</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Attendance</th>
                        <th>Address</th>

                    </tr>

                    </thead>

                    <tbody>
                """);


        String sql =
                "SELECT * FROM students ORDER BY id";


        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {


            while (resultSet.next()) {

                html.append("<tr>");

                html.append("<td>")
                        .append(resultSet.getInt("id"))
                        .append("</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("name")
                                )
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("course")
                                )
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                resultSet.getDouble("marks")
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("email")
                                )
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("phone")
                                )
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                resultSet.getInt("semester")
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("section")
                                )
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                resultSet.getDate("date_of_birth")
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("gender")
                                )
                        )
                        .append("</td>");

                html.append("<td>")
                        .append(
                                resultSet.getDouble("attendance")
                        )
                        .append("%</td>");

                html.append("<td>")
                        .append(
                                escapeHtml(
                                        resultSet.getString("address")
                                )
                        )
                        .append("</td>");

                html.append("</tr>");
            }


        } catch (Exception e) {

            html.append("""
                    <tr>

                        <td colspan="12"
                            class="error">

                            Unable to load student records.

                        </td>

                    </tr>
                    """);

            e.printStackTrace();
        }


        html.append("""
                    </tbody>

                    </table>

                    </div>

                    <a href="/"
                       class="back-link">

                        ← Back to Dashboard

                    </a>

                </div>

                </main>

                <footer>

                    <div>
                        <strong>StudentHub</strong>
                        <span>
                            • Smart Student Management System
                        </span>
                    </div>

                    <div>
                        © 2026 StudentHub
                    </div>

                </footer>

                </body>

                </html>
                """);


        sendResponse(
                exchange,
                200,
                html.toString()
        );
    }


    // =========================================================
    // SEARCH STUDENT
    // =========================================================

    private static void handleSearchStudent(
            HttpExchange exchange
    ) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            sendResponse(
                    exchange,
                    405,
                    "Method Not Allowed"
            );

            return;
        }


        String query =
                exchange.getRequestURI()
                        .getRawQuery();


        Map<String, String> data =
                parseFormData(
                        query == null ? "" : query
                );


        String name =
                safeValue(data.get("name"));


        if (name.isEmpty()) {

            sendResponse(
                    exchange,
                    400,
                    errorPage(
                            "Search Error",
                            "Please enter a student name."
                    )
            );

            return;
        }


        StringBuilder html =
                new StringBuilder();


        html.append("""
                <!DOCTYPE html>
                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">

                    <title>Search Results</title>

                    <link rel="stylesheet"
                          href="/style.css">

                </head>

                <body>

                <header>

                    <h1>🔍 Search Results</h1>

                    <p>
                        Student search results
                    </p>

                </header>

                <main>

                <div class="table-container">

                <table>

                <thead>

                <tr>

                    <th>ID</th>
                    <th>Name</th>
                    <th>Course</th>
                    <th>Marks</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Semester</th>
                    <th>Section</th>
                    <th>Date of Birth</th>
                    <th>Gender</th>
                    <th>Attendance</th>
                    <th>Address</th>

                </tr>

                </thead>

                <tbody>
                """);


        String sql =
                "SELECT * FROM students " +
                "WHERE name LIKE ? " +
                "ORDER BY id";


        boolean found = false;


        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {


            statement.setString(
                    1,
                    "%" + name + "%"
            );


            try (ResultSet resultSet =
                         statement.executeQuery()) {


                while (resultSet.next()) {

                    found = true;

                    html.append("<tr>");

                    html.append("<td>")
                            .append(resultSet.getInt("id"))
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("name")
                                    )
                            )
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("course")
                                    )
                            )
                            .append("</td>");

                    html.append("<td>")
                            .append(resultSet.getDouble("marks"))
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("email")
                                    )
                            )
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("phone")
                                    )
                            )
                            .append("</td>");

                    html.append("<td>")
                            .append(resultSet.getInt("semester"))
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("section")
                                    )
                            )
                            .append("</td>");

                    html.append("<td>")
                            .append(resultSet.getDate("date_of_birth"))
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("gender")
                                    )
                            )
                            .append("</td>");

                    html.append("<td>")
                            .append(
                                    resultSet.getDouble("attendance")
                            )
                            .append("%</td>");

                    html.append("<td>")
                            .append(
                                    escapeHtml(
                                            resultSet.getString("address")
                                    )
                            )
                            .append("</td>");

                    html.append("</tr>");
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    500,
                    errorPage(
                            "Search Error",
                            e.getMessage()
                    )
            );

            return;
        }


        if (!found) {

            html.append("""
                    <tr>

                        <td colspan="12">

                            No student found.

                        </td>

                    </tr>
                    """);
        }


        html.append("""
                </tbody>

                </table>

                </div>

                <a href="/search-student.html"
                   class="back-link">

                    ← Search Again

                </a>

                <br>

                <a href="/"
                   class="back-link">

                    ← Back to Dashboard

                </a>

                </main>

                </body>

                </html>
                """);


        sendResponse(
                exchange,
                200,
                html.toString()
        );
    }


    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    private static void handleUpdateStudent(
            HttpExchange exchange
    ) throws IOException {

        String method =
                exchange.getRequestMethod();


        // -----------------------------------------------------
        // GET: Find student and display update form
        // -----------------------------------------------------

        if (method.equalsIgnoreCase("GET")) {

            String query =
                    exchange.getRequestURI()
                            .getRawQuery();


            Map<String, String> data =
                    parseFormData(
                            query == null ? "" : query
                    );


            String idValue =
                    safeValue(data.get("id"));


            if (idValue.isEmpty()) {

                sendResponse(
                        exchange,
                        400,
                        errorPage(
                                "Update Error",
                                "Student ID is required."
                        )
                );

                return;
            }


            try {

                int id =
                        Integer.parseInt(idValue);


                String sql =
                        "SELECT * FROM students WHERE id = ?";


                try (Connection connection =
                             DatabaseConnection.getConnection();

                     PreparedStatement statement =
                             connection.prepareStatement(sql)) {


                    statement.setInt(1, id);


                    try (ResultSet resultSet =
                                 statement.executeQuery()) {


                        if (resultSet.next()) {

                            String html =
                                    buildUpdateForm(resultSet);

                            sendResponse(
                                    exchange,
                                    200,
                                    html
                            );

                        } else {

                            sendResponse(
                                    exchange,
                                    404,
                                    errorPage(
                                            "Student Not Found",
                                            "No student exists with ID " + id
                                    )
                            );
                        }
                    }
                }


            } catch (Exception e) {

                e.printStackTrace();

                sendResponse(
                        exchange,
                        500,
                        errorPage(
                                "Update Error",
                                e.getMessage()
                        )
                );
            }

            return;
        }


        // -----------------------------------------------------
        // POST: Save updated student information
        // -----------------------------------------------------

        if (method.equalsIgnoreCase("POST")) {

            try {

                String formData =
                        readRequestBody(exchange);


                Map<String, String> data =
                        parseFormData(formData);


                int id =
                        Integer.parseInt(
                                safeValue(data.get("id"))
                        );


                String name =
                        safeValue(data.get("name"));

                String course =
                        safeValue(data.get("course"));

                double marks =
                        Double.parseDouble(
                                safeValue(data.get("marks"))
                        );

                String email =
                        safeValue(data.get("email"));

                String phone =
                        safeValue(data.get("phone"));

                int semester =
                        Integer.parseInt(
                                safeValue(data.get("semester"))
                        );

                String section =
                        safeValue(data.get("section"));

                String dateOfBirth =
                        safeValue(data.get("date_of_birth"));

                String gender =
                        safeValue(data.get("gender"));

                double attendance =
                        Double.parseDouble(
                                safeValue(data.get("attendance"))
                        );

                String address =
                        safeValue(data.get("address"));


                String sql =
                        "UPDATE students SET " +
                        "name=?, course=?, marks=?, email=?, " +
                        "phone=?, semester=?, section=?, " +
                        "date_of_birth=?, gender=?, " +
                        "attendance=?, address=? " +
                        "WHERE id=?";


                try (Connection connection =
                             DatabaseConnection.getConnection();

                     PreparedStatement statement =
                             connection.prepareStatement(sql)) {


                    statement.setString(1, name);
                    statement.setString(2, course);
                    statement.setDouble(3, marks);
                    statement.setString(4, email);
                    statement.setString(5, phone);
                    statement.setInt(6, semester);
                    statement.setString(7, section);
                    statement.setDate(
                            8,
                            Date.valueOf(dateOfBirth)
                    );
                    statement.setString(9, gender);
                    statement.setDouble(10, attendance);
                    statement.setString(11, address);
                    statement.setInt(12, id);


                    int rows =
                            statement.executeUpdate();


                    if (rows > 0) {

                        sendResponse(
                                exchange,
                                200,
                                successPage(
                                        "Student Updated Successfully",
                                        "Student ID " + id +
                                        " has been updated."
                                )
                        );

                    } else {

                        sendResponse(
                                exchange,
                                404,
                                errorPage(
                                        "Student Not Found",
                                        "No student exists with ID " + id
                                )
                        );
                    }
                }


            } catch (Exception e) {

                e.printStackTrace();

                sendResponse(
                        exchange,
                        500,
                        errorPage(
                                "Update Error",
                                e.getMessage()
                        )
                );
            }

            return;
        }


        sendResponse(
                exchange,
                405,
                "Method Not Allowed"
        );
    }


    // =========================================================
    // BUILD UPDATE FORM
    // =========================================================

    private static String buildUpdateForm(
            ResultSet resultSet
    ) throws SQLException {

        int id =
                resultSet.getInt("id");

        String name =
                safeHtmlValue(
                        resultSet.getString("name")
                );

        String course =
                safeHtmlValue(
                        resultSet.getString("course")
                );

        double marks =
                resultSet.getDouble("marks");

        String email =
                safeHtmlValue(
                        resultSet.getString("email")
                );

        String phone =
                safeHtmlValue(
                        resultSet.getString("phone")
                );

        int semester =
                resultSet.getInt("semester");

        String section =
                safeHtmlValue(
                        resultSet.getString("section")
                );

        String dateOfBirth =
                resultSet.getDate("date_of_birth") != null
                        ? resultSet.getDate("date_of_birth").toString()
                        : "";

        String gender =
                safeHtmlValue(
                        resultSet.getString("gender")
                );

        double attendance =
                resultSet.getDouble("attendance");

        String address =
                safeHtmlValue(
                        resultSet.getString("address")
                );


        return """
                <!DOCTYPE html>

                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">

                    <title>Update Student</title>

                    <link rel="stylesheet"
                          href="/style.css">

                </head>

                <body>

                <header>

                    <h1>✏️ Update Student</h1>

                    <p>
                        Update student information
                    </p>

                </header>

                <main>

                <form action="/update-student"
                      method="POST">

                    <input type="hidden"
                           name="id"
                           value="%d">

                    <div class="form-group">

                        <label>Student Name</label>

                        <input type="text"
                               name="name"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Course</label>

                        <input type="text"
                               name="course"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Marks</label>

                        <input type="number"
                               step="0.01"
                               name="marks"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Email</label>

                        <input type="email"
                               name="email"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Phone</label>

                        <input type="text"
                               name="phone"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Semester</label>

                        <input type="number"
                               name="semester"
                               value="%d"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Section</label>

                        <input type="text"
                               name="section"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Date of Birth</label>

                        <input type="date"
                               name="date_of_birth"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Gender</label>

                        <select name="gender"
                                required>

                            <option value="">
                                Select Gender
                            </option>

                            <option value="Male"
                                %s>
                                Male
                            </option>

                            <option value="Female"
                                %s>
                                Female
                            </option>

                            <option value="Other"
                                %s>
                                Other
                            </option>

                        </select>

                    </div>

                    <div class="form-group">

                        <label>Attendance (%)</label>

                        <input type="number"
                               step="0.01"
                               name="attendance"
                               value="%s"
                               required>

                    </div>

                    <div class="form-group">

                        <label>Address</label>

                        <textarea name="address"
                                  required>%s</textarea>

                    </div>

                    <button type="submit">
                        Update Student
                    </button>

                </form>

                <a href="/"
                   class="back-link">

                    ← Back to Dashboard

                </a>

                </main>

                </body>

                </html>
                """.formatted(
                id,
                name,
                course,
                marks,
                email,
                phone,
                semester,
                section,
                dateOfBirth,
                selected(gender, "Male"),
                selected(gender, "Female"),
                selected(gender, "Other"),
                attendance,
                address
        );
    }


    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private static void handleDeleteStudent(
            HttpExchange exchange
    ) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            sendResponse(
                    exchange,
                    405,
                    "Method Not Allowed"
            );

            return;
        }


        try {

            String formData =
                    readRequestBody(exchange);


            Map<String, String> data =
                    parseFormData(formData);


            int id =
                    Integer.parseInt(
                            safeValue(data.get("id"))
                    );


            String sql =
                    "DELETE FROM students WHERE id = ?";


            try (Connection connection =
                         DatabaseConnection.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {


                statement.setInt(1, id);


                int rows =
                        statement.executeUpdate();


                if (rows > 0) {

                    sendResponse(
                            exchange,
                            200,
                            successPage(
                                    "Student Deleted Successfully",
                                    "Student ID " + id +
                                    " has been removed from the database."
                            )
                    );

                } else {

                    sendResponse(
                            exchange,
                            404,
                            errorPage(
                                    "Student Not Found",
                                    "No student exists with ID " + id
                            )
                    );
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    500,
                    errorPage(
                            "Delete Error",
                            e.getMessage()
                    )
            );
        }
    }


    // =========================================================
    // DASHBOARD STATISTICS
    // =========================================================

    private static void handleStatistics(
            HttpExchange exchange
    ) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            sendResponse(
                    exchange,
                    405,
                    "Method Not Allowed"
            );

            return;
        }


        String sql =
                """
                SELECT
                    COUNT(*) AS total_students,
                    COALESCE(AVG(marks), 0) AS average_marks,
                    COALESCE(AVG(attendance), 0)
                        AS average_attendance
                FROM students
                """;


        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {


            if (resultSet.next()) {

                int totalStudents =
                        resultSet.getInt("total_students");

                double averageMarks =
                        resultSet.getDouble("average_marks");

                double averageAttendance =
                        resultSet.getDouble("average_attendance");


                String json =
                        """
                        {
                          "totalStudents": %d,
                          "averageMarks": %.2f,
                          "averageAttendance": %.2f
                        }
                        """.formatted(
                                totalStudents,
                                averageMarks,
                                averageAttendance
                        );


                sendJsonResponse(
                        exchange,
                        200,
                        json
                );

            } else {

                sendJsonResponse(
                        exchange,
                        200,
                        """
                        {
                          "totalStudents": 0,
                          "averageMarks": 0.00,
                          "averageAttendance": 0.00
                        }
                        """
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendJsonResponse(
                    exchange,
                    500,
                    """
                    {
                      "totalStudents": 0,
                      "averageMarks": 0.00,
                      "averageAttendance": 0.00
                    }
                    """
            );
        }
    }


    // =========================================================
    // READ REQUEST BODY
    // =========================================================

    private static String readRequestBody(
            HttpExchange exchange
    ) throws IOException {

        try (InputStream inputStream =
                     exchange.getRequestBody();

             BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     inputStream,
                                     StandardCharsets.UTF_8
                             )
                     )) {


            StringBuilder body =
                    new StringBuilder();

            String line;


            while ((line = reader.readLine()) != null) {

                body.append(line);
            }


            return body.toString();
        }
    }


    // =========================================================
    // PARSE FORM DATA
    // =========================================================

    private static Map<String, String> parseFormData(
            String formData
    ) {

        Map<String, String> data =
                new HashMap<>();


        if (formData == null ||
            formData.isEmpty()) {

            return data;
        }


        String[] pairs =
                formData.split("&");


        for (String pair : pairs) {

            String[] keyValue =
                    pair.split("=", 2);


            if (keyValue.length == 2) {

                try {

                    String key =
                            URLDecoder.decode(
                                    keyValue[0],
                                    StandardCharsets.UTF_8
                            );

                    String value =
                            URLDecoder.decode(
                                    keyValue[1],
                                    StandardCharsets.UTF_8
                            );


                    data.put(key, value);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }


        return data;
    }


    // =========================================================
    // SAFE STRING VALUE
    // =========================================================

    private static String safeValue(
            String value
    ) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }


    // =========================================================
    // HTML ESCAPING
    // =========================================================

    private static String escapeHtml(
            String value
    ) {

        if (value == null) {
            return "";
        }


        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }


    // =========================================================
    // SAFE HTML VALUE
    // =========================================================

    private static String safeHtmlValue(
            String value
    ) {

        return escapeHtml(
                value == null ? "" : value
        );
    }


    // =========================================================
    // SELECTED OPTION
    // =========================================================

    private static String selected(
            String currentValue,
            String optionValue
    ) {

        if (currentValue != null &&
            currentValue.equalsIgnoreCase(optionValue)) {

            return "selected";
        }

        return "";
    }


    // =========================================================
    // SEND HTML FILE
    // =========================================================

    private static void sendFile(
            HttpExchange exchange,
            String filePath,
            String contentType
    ) throws IOException {

        try {

            Path path =
                    Path.of(filePath);


            if (!Files.exists(path)) {

                sendResponse(
                        exchange,
                        404,
                        "File not found: " + filePath
                );

                return;
            }


            byte[] content =
                    Files.readAllBytes(path);


            exchange.getResponseHeaders()
                    .set(
                            "Content-Type",
                            contentType + "; charset=UTF-8"
                    );


            exchange.sendResponseHeaders(
                    200,
                    content.length
            );


            try (OutputStream outputStream =
                         exchange.getResponseBody()) {

                outputStream.write(content);
            }


        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(
                    exchange,
                    500,
                    "Unable to load file."
            );
        }
    }


    // =========================================================
    // SEND HTML RESPONSE
    // =========================================================

    private static void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response
    ) throws IOException {

        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );


        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "text/html; charset=UTF-8"
                );


        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );


        try (OutputStream outputStream =
                     exchange.getResponseBody()) {

            outputStream.write(bytes);
        }
    }


    // =========================================================
    // SEND JSON RESPONSE
    // =========================================================

    private static void sendJsonResponse(
            HttpExchange exchange,
            int statusCode,
            String json
    ) throws IOException {

        byte[] bytes =
                json.getBytes(
                        StandardCharsets.UTF_8
                );


        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );


        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );


        try (OutputStream outputStream =
                     exchange.getResponseBody()) {

            outputStream.write(bytes);
        }
    }


    // =========================================================
    // SUCCESS PAGE
    // =========================================================

    private static String successPage(
            String title,
            String message
    ) {

        return """
                <!DOCTYPE html>

                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">

                    <title>%s</title>

                    <link rel="stylesheet"
                          href="/style.css">

                </head>

                <body>

                <header>

                    <h1>✅ %s</h1>

                    <p>
                        Smart Student Management System
                    </p>

                </header>

                <main>

                    <div class="search-card">

                        <div class="search-icon">
                            ✅
                        </div>

                        <h2>
                            %s
                        </h2>

                        <p class="search-description">
                            %s
                        </p>

                        <a href="/"
                           class="back-link">

                            ← Back to Dashboard

                        </a>

                    </div>

                </main>

                </body>

                </html>
                """.formatted(
                        escapeHtml(title),
                        escapeHtml(title),
                        escapeHtml(title),
                        escapeHtml(message)
                );
    }


    // =========================================================
    // ERROR PAGE
    // =========================================================

    private static String errorPage(
            String title,
            String message
    ) {

        return """
                <!DOCTYPE html>

                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">

                    <title>%s</title>

                    <link rel="stylesheet"
                          href="/style.css">

                </head>

                <body>

                <header>

                    <h1>❌ %s</h1>

                    <p>
                        Smart Student Management System
                    </p>

                </header>

                <main>

                    <div class="search-card">

                        <div class="search-icon">
                            ⚠️
                        </div>

                        <h2>
                            %s
                        </h2>

                        <p class="search-description">
                            %s
                        </p>

                        <a href="/"
                           class="back-link">

                            ← Back to Dashboard

                        </a>

                    </div>

                </main>

                </body>

                </html>
                """.formatted(
                        escapeHtml(title),
                        escapeHtml(title),
                        escapeHtml(title),
                        escapeHtml(
                                message == null
                                        ? "An unexpected error occurred."
                                        : message
                        )
                );
    }
}
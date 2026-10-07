# Smart Student Management System

## StudentHub

A Java-based web application for managing student records using MySQL database and JDBC connectivity.

---

## 1. Project Overview

The Smart Student Management System is designed to provide a simple and user-friendly platform for managing student information.

The system allows users to:

- Add new student records
- View all students
- Search students
- Update student information
- Delete student records
- View live student statistics
- Store and retrieve data from a MySQL database

The project demonstrates Java programming, JDBC database connectivity, SQL operations, and web-based user interface development.

---

## 2. Problem Statement

Managing student information manually can be time-consuming and difficult to maintain.

This project provides a centralized system where student records can be stored, searched, updated, and deleted efficiently using a MySQL database.

---

## 3. Objectives

The main objectives of the project are:

- To develop a simple student management system
- To implement database connectivity using JDBC
- To perform CRUD operations on student records
- To create a clean and responsive web interface
- To understand Java-based web application development
- To maintain organized and reusable project code

---

## 4. Features

### Dashboard

The dashboard provides quick access to the major features of the system.

### Add Student

Users can add a new student with details such as:

- Student Name
- Course
- Marks
- Email
- Phone
- Semester
- Section
- Date of Birth
- Gender
- Attendance
- Address

### View Students

Displays student records retrieved directly from the MySQL database.

### Search Student

Allows users to search for a student by name.

### Update Student

Allows users to update existing student information using the Student ID.

### Delete Student

Allows users to delete a student record using the Student ID.

### Live Statistics

The dashboard displays:

- Total Students
- Average Marks
- Average Attendance

These statistics are calculated from the database.

---

## 5. Technology Stack

### Programming Language

- Java

### Database

- MySQL

### Database Connectivity

- JDBC
- MySQL Connector/J 26.7.0

### Frontend

- HTML5
- CSS3
- JavaScript

### Web Server

- Java `com.sun.net.httpserver.HttpServer`

### Development Environment

- Visual Studio Code
- JDK

---

## 6. Project Structure

```text
Smart Student Management System
│
├── src
│   └── main
│       └── java
│           └── com
│               └── student
│                   ├── Student.java
│                   ├── StudentManager.java
│                   ├── Main.java
│                   ├── DatabaseConnection.java
│                   ├── StudentServer.java
│                   │
│                   └── web
│                       ├── index.html
│                       ├── style.css
│                       ├── add-student.html
│                       ├── students.html
│                       ├── search-student.html
│                       ├── update-student.html
│                       └── delete-student.html
│
├── lib
│   └── mysql-connector-j-26.7.0.jar
│
├── bin
│
└── README.md
package com.student;

public class Student {

    private int id;
    private String name;
    private String course;
    private double marks;
    private String email;
    private String phone;
    private int semester;
    private String section;
    private String dateOfBirth;
    private String gender;
    private double attendance;
    private String address;

    public Student(
            int id,
            String name,
            String course,
            double marks,
            String email,
            String phone,
            int semester,
            String section,
            String dateOfBirth,
            String gender,
            double attendance,
            String address) {

        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
        this.email = email;
        this.phone = phone;
        this.semester = semester;
        this.section = section;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.attendance = attendance;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getSemester() {
        return semester;
    }

    public String getSection() {
        return section;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public double getAttendance() {
        return attendance;
    }

    public String getAddress() {
        return address;
    }
}
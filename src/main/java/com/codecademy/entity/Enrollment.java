package com.codecademy.entity;

public class Enrollment {
    private Student student;
    private Course course;
    private Certificate certificate;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Missing student");
        }
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Missing course");
        }
        this.course = course;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        if (certificate == null) {
            throw new IllegalArgumentException("Missing certificate");
        }
        this.certificate = certificate;
    }
}

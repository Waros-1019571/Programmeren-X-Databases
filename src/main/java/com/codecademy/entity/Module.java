package com.codecademy.entity;

import java.util.Date;
import java.util.HashMap;

public class Module {
    private int courseID;
    private String title;
    private String description;
    private int serialNumber;
    private Date publicationDate;
    private String version;
    private final HashMap<Student, Double> progressOfStudents;
    private final Module module;

    public Module(int courseID, String title, String description, int serialNumber, Date publicationDate,
                  String version, Module module) {
        this.courseID = courseID;
        this.title = title;
        this.description = description;
        this.serialNumber = serialNumber;
        this.publicationDate = publicationDate;
        this.version = version;
        this.module = module;
        this.progressOfStudents = new HashMap<>();
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<Student, Double> getProgressOfStudents() {
        return progressOfStudents;
    }

    public Module getModule() {
        return module;
    }
}

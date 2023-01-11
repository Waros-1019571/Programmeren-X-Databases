package com.codecademy.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;
import java.util.HashMap;

public class Module {
    private SimpleIntegerProperty courseID;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleIntegerProperty serialNumber;
    private SimpleObjectProperty publicationDate;
    private SimpleStringProperty version;
    private final HashMap<Student, Double> progressOfStudents;
    private final Module module;

    public Module(int courseID, String title, String description, int serialNumber, Date publicationDate,
                  String version, Module module) {
        this.courseID = new SimpleIntegerProperty();

        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.serialNumber = new SimpleIntegerProperty();
        this.publicationDate = new SimpleObjectProperty();
        this.version = new SimpleStringProperty();
        this.module = module;
        this.progressOfStudents = new HashMap<>();
    }

    public int getCourseID() {
        return courseID.get();
    }

    public void setCourseID(int courseID) {
        this.courseID.set(courseID);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getSerialNumber() {
        return serialNumber.get();
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber.set(serialNumber);
    }

    public Date getPublicationDate() {
        return (Date) publicationDate.get();
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate.set(publicationDate);
    }

    public String getVersion() {
        return version.get();
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public HashMap<Student, Double> getProgressOfStudents() {
        return progressOfStudents;
    }

    public Module getModule() {
        return module;
    }
}

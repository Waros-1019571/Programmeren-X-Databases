package com.codecademy.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableMap;

import java.util.Date;
import java.util.HashMap;

public class Module {

    private SimpleIntegerProperty ID;
    private SimpleIntegerProperty courseID;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleStringProperty serialNumber;
    private SimpleObjectProperty publicationDate;
    private SimpleStringProperty version;
    private SimpleMapProperty<Student, Double> progressOfStudents;
    private Module module;
    private SimpleStringProperty contactName;
    private SimpleStringProperty contactEmail;


    public Module() {
        this.ID = new SimpleIntegerProperty();
        this.courseID = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.serialNumber = new SimpleStringProperty();
        this.publicationDate = new SimpleObjectProperty();
        this.version = new SimpleStringProperty();
//        this.module = new Simple<>()
        this.progressOfStudents = new SimpleMapProperty<>();
        this.contactEmail = new SimpleStringProperty();
        this.contactName = new SimpleStringProperty();
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

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public void setSerialNumber(String serialNumber) {
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

    public Module getModule() {
        return module;
    }

    public ObservableMap<Student, Double> getProgressOfStudents() {
        return progressOfStudents.get();
    }

    public String getContactName() {
        return contactName.get();
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public String getContactEmail() {
        return contactEmail.get();
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail.set(contactEmail);
    }

    public int getID() {
        return ID.get();
    }
    public void setID(int ID) {
        this.ID.set(ID);
    }
}

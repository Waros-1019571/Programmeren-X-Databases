package com.codecademy.entity;

import com.codecademy.logic.NumericRangeTools;
import com.codecademy.logic.StringTools;

import java.sql.Date;
import java.time.LocalDate;

public class Module {

    private int ID;
    private String title;
    private String description;
    private Date publicationDate;
    private double version;
    private String contactName;
    private String contactEmail;

    private Course course;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        if (!NumericRangeTools.isPositiveNumber(ID)) {
            throw new IllegalArgumentException("ID is negative");
        }
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!StringTools.isNotNullEmptyOrWhitespace(title)) {
            throw new IllegalArgumentException("Title is empty");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (!StringTools.isNotNullEmptyOrWhitespace(description)) {
            throw new IllegalArgumentException("Description is empty");
        }
        this.description = description;
    }

    public LocalDate getPublicationDate() {
        return publicationDate.toLocalDate();
    }

    public void setPublicationDate(Date publicationDate) {
        if (publicationDate == null) {
            throw new IllegalArgumentException("Publication date is empty");
        }
        this.publicationDate = publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate == null) {
            throw new IllegalArgumentException("Publication date is empty");
        }
        this.publicationDate = Date.valueOf(publicationDate);
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(String version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version is empty");
        }
        try {
            setVersion(Double.parseDouble(version));
        } catch (Exception e ) {
            throw new IllegalArgumentException("Version is not a decimal");
        };
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        if (!StringTools.isNotNullEmptyOrWhitespace(contactName)) {
            throw new IllegalArgumentException("Contact name is empty");
        }
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        if (!StringTools.isNotNullEmptyOrWhitespace(contactEmail)) {
            throw new IllegalArgumentException("Contact email is empty");
        }
        this.contactEmail = contactEmail;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course is empty");
        }
        this.course = course;
    }
}

package com.codecademy.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;

public class Webcast {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleStringProperty url;
    private SimpleObjectProperty publicationDate;
    private SimpleIntegerProperty duration;
    private VoiceActor voiceActor;
    private Course course;
    private HashMap<Student, Double> progressOfStudent = new HashMap<Student, Double>();

    public Webcast() {
        id = new SimpleIntegerProperty();
        title = new SimpleStringProperty();
        description = new SimpleStringProperty();
        url = new SimpleStringProperty();
        duration = new SimpleIntegerProperty();
        publicationDate = new SimpleObjectProperty();
    }

    public void addStudentProgress (Student student, Double progress) {
        progressOfStudent.put(student, progress);
    }

    public int getDuration() {
        return duration.get();
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public LocalDate getPublicationDate() {
        return (LocalDate) publicationDate.get();
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate.set(publicationDate);
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate.set(Date.valueOf(publicationDate));
    }

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public VoiceActor getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(VoiceActor voiceActor) {
        this.voiceActor = voiceActor;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
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
        this.description.set(description);}

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

package com.codecademy.entity;

import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;

public class Webcast {
    private int id;
    private String title;
    private String description;
    private String url;
    private Date publicationDate;
    private int duration;
    private VoiceActor voiceActor;
    private Course course;
    private HashMap<Student, Double> progressOfStudent = new HashMap<Student, Double>();

    public Webcast() {

    }

    public void addStudentProgress (Student student, Double progress) {
        progressOfStudent.put(student, progress);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public LocalDate getPublicationDate() {
        return publicationDate.toLocalDate();
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = Date.valueOf(publicationDate);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VoiceActor getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(VoiceActor voiceActor) {
        this.voiceActor = voiceActor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

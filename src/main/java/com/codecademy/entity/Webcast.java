package com.codecademy.entity;

import java.util.Date;
import java.util.HashMap;

public class Webcast {
    private int duration;
    private Date publicationDate;
    private String url;
    private HashMap<Student, Double> progressOfStudent = new HashMap<Student, Double>();
    private VoiceActor voiceActor;

    public Webcast(int duration, Date publicationDate, String url, VoiceActor voiceActor) {
        this.duration = duration;
        this.publicationDate = publicationDate;
        this.url = url;
        this.voiceActor = voiceActor;
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

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
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
}

package com.codecademy.entity;

import com.codecademy.logic.NumericRangeTools;
import com.codecademy.logic.StringTools;

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

    public void addStudentProgress (Student student, double progress) {
        if (student == null) {
            throw new NullPointerException("Missing student");
        }
        if (!NumericRangeTools.isValidPercentage(progress)) {
            throw new IllegalArgumentException("Progress is not a valid percentage");
        }
        progressOfStudent.put(student, progress);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (!NumericRangeTools.isPositiveNumber(duration)) {
            throw new IllegalArgumentException("Duration is not a positive number");
        }
        this.duration = duration;
    }

    public LocalDate getPublicationDate() {
        return publicationDate.toLocalDate();
    }

    public void setPublicationDate(Date publicationDate) {
        if (publicationDate == null) {
            throw new NullPointerException("Missing publication date");
        }
        this.publicationDate = publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate == null) {
            throw new NullPointerException("Missing publication date");
        }
        this.publicationDate = Date.valueOf(publicationDate);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (!StringTools.isNotNullEmptyOrWhitespace(url)) {
            throw new IllegalArgumentException("URL is empty");
        }
        this.url = url;
    }

    public VoiceActor getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(VoiceActor voiceActor) {
        if (voiceActor == null) {
            throw new IllegalArgumentException("Missing voice actor");
        }
        this.voiceActor = voiceActor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (!NumericRangeTools.isPositiveNumber(id)) {
            throw new IllegalArgumentException("ID is not a positive number");
        }
        this.id = id;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Missing course");
        }
        this.course = course;
    }
}

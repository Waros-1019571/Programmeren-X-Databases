package com.codecademy.entity;

public class Course {
    private int courseId;
    private String title;
    private String topic;
    private String ownerName;
    private String ownerEmail;
    private String description;
    private int courseLevel;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(int courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseLevelToString() {
        if (courseLevel == 0) {
            return "Beginner";
        }
        if (courseLevel == 1) {
            return "Intermediate";
        }
        if (courseLevel == 2) {
            return "Expert";
        }
        return "Unknown";
    }

}
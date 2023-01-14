package com.codecademy.entity;

import com.codecademy.logic.MailTools;
import com.codecademy.logic.NumericRangeTools;
import com.codecademy.logic.StringTools;

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
        if (!NumericRangeTools.isPositiveNumber(courseId)) {
            throw new IllegalArgumentException("Negative ID");
        }
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (!StringTools.isNotNullEmptyOrWhitespace(title)) {
            throw new IllegalArgumentException("Missing title");
        }
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        if (!StringTools.isNotNullEmptyOrWhitespace(topic)) {
            throw new IllegalArgumentException("Missing topic");
        }
        this.topic = topic;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (!StringTools.isNotNullEmptyOrWhitespace(ownerName)) {
            throw new IllegalArgumentException("Missing owner name");
        }
        this.ownerName = ownerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (!StringTools.isNotNullEmptyOrWhitespace(description)) {
            throw new IllegalArgumentException("Missing description");
        }
        this.description = description;
    }

    public int getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(int courseLevel) {
        if (!NumericRangeTools.isWithinRange(courseLevel, 0 ,2)) {
            throw new IllegalArgumentException("Course level not valid");
        }
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

    @Override
    public String toString() {
        return title;
    }
}
package com.codecademy.entity;

import com.codecademy.logic.NumericRangeTools;

public class WebcastProgression {
    private Webcast webcast;
    private Student student;
    private double progress;

    public Webcast getWebcast() {
        return webcast;
    }

    public void setWebcast(Webcast webcast) {
        if (webcast == null) {
            throw new IllegalArgumentException("Missing webcast");
        }
        this.webcast = webcast;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Missing student");
        }
        this.student = student;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        if (!NumericRangeTools.isValidPercentage(progress)) {
            throw new IllegalArgumentException("Progress is not a valid percentage");
        }
        this.progress = progress;
    }
}

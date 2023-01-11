package com.codecademy.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
    private SimpleIntegerProperty courseId;
    private SimpleStringProperty title;
    private SimpleStringProperty topic;
    private SimpleStringProperty courseOwnerName;
    private SimpleStringProperty description;
    private SimpleStringProperty name;
    private SimpleIntegerProperty courseLevel;
    private SimpleIntegerProperty courseStatus;
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getCourseLevel() {
        return courseLevel.get();
    }

    public void setCourseLevel(int courseLevel) {
        this.courseLevel.set(courseLevel);
    }

    public int getCourseStatus() {
        return courseStatus.get();
    }


    public void setCourseStatus(int courseStatus) {
        this.courseStatus.set(courseStatus);
    }



    public Course() {
        this.courseId = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.topic = new SimpleStringProperty();
        this.courseOwnerName = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
    }

    //    public int getStatisticOfPerson(Person student){
//        return 0;
//    }
//
//    public void enrollStudent(Person student){
//
//    }
//
//    public void deenrollStudent(Person student){
//
//    }

//    public ArrayList<Webcast> getTopWebcast(){
//        return ArrayList<Webcast>;
//    }
//
//    public ArrayList<Course> getRecommendations(){
//        return ArrayList<Course>;
//    }

    //------------------ Default getters and setters

    public int getCourseId() {
        return courseId.get();
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getCourseOwnerName() {
        return courseOwnerName.get();
    }

    public void setCourseOwnerName(String courseOwnerName) {
        this.courseOwnerName.set(courseOwnerName);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
package src.java.com.codecademy.packageName;

import java.util.ArrayList;

public class Course {
    private int courseId;
    private String title;
    private String topic;
    private String courseOwnerName;
    private String description;


    public int getStatisticOfPerson(Person student){
        return 0;
    }

    public void enrollStudent(Person student){

    }

    public void deenrollStudent(Person student){

    }

    public ArrayList<Webcast> getTopWebcast(){
        return ArrayList<Webcast>;
    }

    public ArrayList<Course> getRecommendations(){
        return ArrayList<Course>;
    }

    //------------------ Default getters and setters

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

    public String getCourseOwnerName() {
        return courseOwnerName;
    }

    public void setCourseOwnerName(String courseOwnerName) {
        this.courseOwnerName = courseOwnerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
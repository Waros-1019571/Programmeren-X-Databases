package com.codecademy.model;

import java.util.Date;
import java.util.HashMap;

public class Webcast {
    private int duration;
    private Date publicationDate;
    private String url;
    //private HashMap<Student, Double> progressOfStudent = new HashMap<Student, Double>();//
    private VoiceActor voiceActor;

    public Webcast(int duration, Date publicationDate, String url, VoiceActor voiceActor) {
        this.duration = duration;
        this.publicationDate = publicationDate;
        this.url = url;
        this.voiceActor = voiceActor;
    }

}

package com.codecademy.entity;

public class VoiceActor {
    private String name;

    private int Id;

    public VoiceActor() {}

    public String getName() {
        return name;
    }

    public void setVoiceActorName(String name) {
        this.name = name;
    }

    public void setVoiceActorId(int Id) {
        this.Id = Id;
    }

    public int getVoiceActorId() {
        return Id;
    }
}

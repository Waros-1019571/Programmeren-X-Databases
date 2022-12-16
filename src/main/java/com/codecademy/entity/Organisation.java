package com.codecademy.entity;

public class Organisation {
    private int organisationId;
    private String name;

    public Organisation(int organisationId, String name) {
        this.organisationId = organisationId;
        this.name = name;
    }

    //------------------ Default getters and setters

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
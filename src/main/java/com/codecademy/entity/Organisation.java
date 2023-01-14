package com.codecademy.entity;

import com.codecademy.logic.StringTools;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Organisation {
    private SimpleIntegerProperty organisationId;
    private SimpleStringProperty name;

    public Organisation() {
        this.organisationId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public int getOrganisationId() {
        return organisationId.get();
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId.set(organisationId);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        if (!StringTools.isNotNullEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException("Name is empty");
        }
        this.name.set(name);
    }

    @Override
    public String toString() {
        return getName();
    }
}
package com.codecademy.entity;

import com.codecademy.logic.NumericRangeTools;
import com.codecademy.logic.StringTools;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VoiceActor {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private Organisation organisation;

    public VoiceActor() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        if (!NumericRangeTools.isPositiveNumber(id)) {
            throw new IllegalArgumentException("ID is negative");
        }
        this.id.set(id);
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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        if (organisation == null) {
            throw new IllegalArgumentException("Missing organisation");
        }
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return getName();
    }
}

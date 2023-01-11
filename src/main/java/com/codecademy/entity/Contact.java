package com.codecademy.entity;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty name;
    private SimpleStringProperty email;

    public Contact(String name, String email) {
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}

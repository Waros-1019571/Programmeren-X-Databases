package com.codecademy.entity;

import com.codecademy.logic.MailTools;
import com.codecademy.logic.StringTools;
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
        if (!StringTools.isNotNullEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException("Name is empty");
        }
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        if (!MailTools.validateMailAddress(email)) {
            throw new IllegalArgumentException("Email address invalid");
        }
        this.email.set(email);
    }
}

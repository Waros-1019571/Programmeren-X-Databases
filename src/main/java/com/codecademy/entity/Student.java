/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.entity;

import com.codecademy.logic.MailTools;
import com.codecademy.logic.PostalCode;
import com.codecademy.logic.StringTools;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Daan
 */
public class Student {
    private int id;
    private String email;
    private String name;
    private LocalDate birthDate;
    private int gender;
    private String address;
    private String postalCode;
    private String city;
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!MailTools.validateMailAddress(email)) {
            throw new IllegalArgumentException("Email address invalid");
        }
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!StringTools.isNotNullEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException("Name is empty");
        }
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is empty");
        }
        this.birthDate = birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate.toLocalDate();
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        if (gender < 0 || gender > 2) {
            throw new IllegalArgumentException("Invalid gender");
        }
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!StringTools.isNotNullEmptyOrWhitespace(address)) {
            throw new IllegalArgumentException("Address is empty");
        }
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) throws IllegalArgumentException, NullPointerException {
        this.postalCode = PostalCode.formatPostalCode(postalCode);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (!StringTools.isNotNullEmptyOrWhitespace(city)) {
            throw new IllegalArgumentException("City is empty");
        }
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (!StringTools.isNotNullEmptyOrWhitespace(country)) {
            throw new IllegalArgumentException("Country is empty");
        }
        this.country = country;
    }

    public String genderToString() {
        if (gender == 0) {
            return "Other";
        }
        if (gender == 1) {
            return "Female";
        }
        if (gender == 2) {
            return "Male";
        }
        return "Unknown";
    }
}

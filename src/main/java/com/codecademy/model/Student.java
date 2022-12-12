/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.model;

import java.util.Date;

/**
 *
 * @author Daan
 */
public class Student {

    private String email;
    private String name;
    private Date birthDate;
    private String gender;
    private String street;
    private String postalCode;
    private String houseNumber;
    private String city;
    private String country;

    public Student(String email, String name, Date birthDate, String gender, String street, String postalCode, String houseNumber, String city, String country) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.street = street;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
        this.city = city;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
//

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
//

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date newBirthDate) {
        this.birthDate = newBirthDate;
    }
//

    public String getGender() {
        return gender;
    }

    public void setGender(String newGender) {
        this.gender = newGender;
    }

//
    public String getStreet() {
        return street;
    }

    public void setStreet(String newStreet) {
        this.street = newStreet;
    }

//
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String newPostalCode) {
        this.postalCode = newPostalCode;
    }

//
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String newHouseNumber) {
        this.houseNumber = newHouseNumber;
    }

//
    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

//
    public String getCountry() {
        return country;
    }

    public void setCountry(String newCountry) {
        this.country = newCountry;
    }
}

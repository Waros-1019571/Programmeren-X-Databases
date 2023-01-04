/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.entity;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
/**
 *
 * @author Daan
 */
public class Student {

    private SimpleStringProperty email;
    private SimpleStringProperty name;
    private SimpleObjectProperty birthDate;
    private SimpleStringProperty gender;
    private SimpleStringProperty street;
    private SimpleStringProperty postalCode;
    private SimpleStringProperty houseNumber;
    private SimpleStringProperty city;
    private SimpleStringProperty country;

    public Student() {
        this.email = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.birthDate = new SimpleObjectProperty();
        this.gender = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.houseNumber = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
//

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
//

    public Date getBirthDate() {
        return (Date) birthDate.get();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate.set(birthDate);
    }
//

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

//
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

//
    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

//
    public String getHouseNumber() {
        return houseNumber.get();
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber.set(houseNumber);
    }

//
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

//
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    private static class birthDate {

        public birthDate() {
        }
    }
}

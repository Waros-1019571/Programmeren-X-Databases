/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.entity;

import com.codecademy.logic.NumericRangeTools;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Daan
 */
public class Certificate {

    private SimpleIntegerProperty certificateId;
    private SimpleDoubleProperty grade;
    private SimpleStringProperty adress;

    public Certificate() {
        this.certificateId = new SimpleIntegerProperty();
        this.grade = new SimpleDoubleProperty();
        this.adress = new SimpleStringProperty()
    }

    public int getCertificateId() {
        return certificateId.get();
    }

    public void setCertificateId(int certificateId) {
        if (!NumericRangeTools.isPositiveNumber(certificateId)) {
            throw new IllegalArgumentException("ID is negative");
        }
        this.certificateId.set(certificateId);
    }

    public double getGrade() {
        return grade.get();
    }

    public void setGrade(double grade) {
        if (!NumericRangeTools.isWithinRange(grade, 1, 10)) {
            throw new IllegalArgumentException("Grade must be between a 1.0 and 10.0");
        }
        this.grade.set(grade);
    }

    public String getAdress() {
        return adress.get();
    }
    
    public void setAdress(String adress) {
        this.adress.set(adress);
    }
}

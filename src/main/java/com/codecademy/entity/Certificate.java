/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
/**
 *
 * @author Daan
 */
public class Certificate {

    private SimpleIntegerProperty certificateId;
    private SimpleDoubleProperty grade;

    public Certificate() {
        this.certificateId = new SimpleIntegerProperty();
        this.grade = new SimpleDoubleProperty();
    }

    public int getCertificateId() {
        return certificateId.get();
    }

    public void setCertificateId(int certificateId) {
        this.certificateId.set(certificateId);
    }
//

    public double getGrade() {
        return grade.get();
    }

    public void setGrade(double grade) {
        this.grade.set(grade);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.entity;

/**
 *
 * @author Daan
 */
public class Certificate {

    private int certificateId;
    private double grade;

    public Certificate(int certificateId, double grade) {
        this.certificateId = certificateId;
        this.grade = grade;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int newCertificateId) {
        this.certificateId = newCertificateId;
    }
//

    public double getGrade() {
        return grade;
    }

    public void setGrade(double newGrade) {
        this.grade = newGrade;
    }
}

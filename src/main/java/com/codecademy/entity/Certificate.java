/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codecademy.entity;

import com.codecademy.logic.NumericRangeTools;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Daan
 */
public class Certificate {

    private int certificateId;
    private Date certificationDate;
    private double grade;
    private String signedBy;

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        if (!NumericRangeTools.isPositiveNumber(certificateId)) {
            throw new IllegalArgumentException("ID is negative");
        }
        this.certificateId = certificateId;
    }

    public Date getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(Date certificationDate) {
        this.certificationDate = certificationDate;
    }

    public void setCertificationDate(LocalDate certificationDate) {
        this.certificationDate = Date.valueOf(certificationDate);
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        if (grade == null || grade.isEmpty()) {
            throw new IllegalArgumentException("Grade is empty");
        }
        try {
            setGrade(Double.parseDouble(grade));
        } catch (Exception e) {
            throw new IllegalArgumentException("Grade is not a decimal");
        }
    }

    public void setGrade(double grade) {
        if (!NumericRangeTools.isWithinRange(grade, 0, 10)) {
            throw new IllegalArgumentException("Grade must be between a 0 and 10.0");
        }
        this.grade = grade;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        if (signedBy == null || signedBy.isEmpty()) {
            throw new IllegalArgumentException("Certification has to be signed by someone");
        }
        this.signedBy = signedBy;
    }
}

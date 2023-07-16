package edu.pet_project.studentorder.domain;

import java.time.LocalDate;
import java.util.Date;

public class StudentOrder {
   private long studentOrderId;
   private Adult husband;
   private Adult wife;
   private Child child;
    private String MarriageCertificateId;

    private LocalDate MarriageDate;

    private String MerrigeOfice;

    public String getMerrigeOfice() {
        return MerrigeOfice;
    }

    public void setMerrigeOfice(String merrigeOfice) {
        MerrigeOfice = merrigeOfice;
    }

    public LocalDate getMarriageDate() {
        return MarriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        MarriageDate = marriageDate;
    }

    public String getMarriageCertificateId() {
        return MarriageCertificateId;
    }

    public void setMarriageCertificateId(String marriageCertificateId) {
        MarriageCertificateId = marriageCertificateId;
    }




    public long getStudentOrderId() {
        return studentOrderId;
    }

    public void setStudentOrderId(long studentOrderId) {
        this.studentOrderId = studentOrderId;
    }

    public Adult getHusband() {
        return husband;
    }

    public void setHusband(Adult husband) {
        this.husband = husband;
    }

    public Adult getWife() {
        return wife;
    }

    public void setWife(Adult wife) {
        this.wife = wife;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }


}

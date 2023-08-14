package edu.pet_project.studentorder.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentOrder {
   private long studentOrderId;
    private StudentOrderStatus studentOrderStatus;
   private LocalDateTime studentOrderDate;
   private Adult husband;
    private Adult wife;
    private List<Child> children;
    private String MarriageCertificateId;
    private RegisterOffice marriageOffice;
    private LocalDate MarriageDate;

    public StudentOrderStatus getStudentOrderStatus() {
        return studentOrderStatus;
    }

    public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
        this.studentOrderStatus = studentOrderStatus;
    }

    public LocalDateTime getStudentOrderDate() {
        return studentOrderDate;
    }

    public void setStudentOrderDate(LocalDateTime studentOrderDate) {
        this.studentOrderDate = studentOrderDate;
    }



    public void addChild(Child child){
        if (children ==null){
            children = new ArrayList<>(5); // создаем ArrayList c числом ячеек 5
        }
        children.add(child);
    }
    public List<Child> getChildren() {
        return children;
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

    public RegisterOffice getMarriageOffice() {
        return marriageOffice;
    }

    public void setMarriageOffice(RegisterOffice marriageOffice) {
        this.marriageOffice = marriageOffice;
    }
}

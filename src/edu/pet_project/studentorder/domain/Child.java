package edu.pet_project.studentorder.domain;

import java.time.LocalDate;

public class Child extends Person{
    private  String certificateNumber; //свидетельство о рождении
    private LocalDate issueDate; //дата выдачи
    private RegisterOffice issueDepartment; //место выдачи

    public Child(String surname, String givenName, String patronomyc, LocalDate dateOfBithday) {
        super(surname, givenName, patronomyc, dateOfBithday);
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public RegisterOffice getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(RegisterOffice issueDepartment) {
        this.issueDepartment = issueDepartment;
    }
}

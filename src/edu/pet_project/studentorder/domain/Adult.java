package edu.pet_project.studentorder.domain;

import java.time.LocalDate;

public class Adult extends Person {
    private String passportSerial;
    private String passportNumber;
    private LocalDate issueDate; //дата выдачи
    private PassportOffice issueDepartment; //место выдачи
    private University university; // название универа
    private String studentId; // Идентификатор студенческого билета

    public Adult() {
    }

    public Adult(String surname, String givenName, String patronomyc, LocalDate dateOfBithday) {
        super(surname, givenName, patronomyc, dateOfBithday);
    }


    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassportSerial() {
        return passportSerial;
    }

    public void setPassportSerial(String passportSerial) {
        this.passportSerial = passportSerial;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public PassportOffice getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(PassportOffice issueDepartment) {
        this.issueDepartment = issueDepartment;
    }
}

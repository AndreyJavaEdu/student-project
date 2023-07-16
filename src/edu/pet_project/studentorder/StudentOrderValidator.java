package edu.pet_project.studentorder;

import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.mail.MailSender;
import edu.pet_project.studentorder.validator.ChildrenValidator;
import edu.pet_project.studentorder.validator.CityRegisterValidator;
import edu.pet_project.studentorder.validator.StudentValidator;
import edu.pet_project.studentorder.validator.WeddingValidator;

public class StudentOrderValidator {
    private CityRegisterValidator cityRegisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator(){
        cityRegisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
        System.out.println();
        System.out.println();

    }
    public void checkAll() {
        StudentOrder[] soArray = readStudentOrders();
        for (int j=0; j<soArray.length; j++){
            checkOneOrder(soArray[j]);
            System.out.println();
        }

    }

    public StudentOrder[] readStudentOrders() {
        StudentOrder[] soArray = new StudentOrder[3];
        for (int j=0; j<soArray.length; j++){
            soArray[j]= SaveStudentOrder.buildStudentOrder(j);
        }
        return soArray;
    }

    public void checkOneOrder (StudentOrder so){
            AnswerCityRegister cityRegister = checkCityRegister(so);
            AnswerChildren childAnswer = chekChildren(so);
            AnswerStudent  studentAnswer = checkStudent(so);
            AnswerWedding weddingAnswer = checkWedding(so);
            sendMail(so);
    }








    public AnswerCityRegister checkCityRegister(StudentOrder so){

       return cityRegisterVal.checkCityRegister(so);
    }
    public AnswerWedding checkWedding(StudentOrder so){
        return weddingVal.checkWedding(so);
    }
    public AnswerChildren chekChildren(StudentOrder so){
        return childrenVal.chekChildren(so);
    }
    public AnswerStudent checkStudent(StudentOrder so){
        return studentVal.checkStudent(so);
    }

    public void sendMail (StudentOrder so){
        new MailSender().sendMail(so);
    }
}

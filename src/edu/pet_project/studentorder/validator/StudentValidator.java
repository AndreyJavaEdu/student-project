package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerStudent;
import edu.pet_project.studentorder.domain.StudentOrder;

public class StudentValidator {
    String hostName;
    String login;
    String password;

     public AnswerStudent checkStudent(StudentOrder so){
        System.out.println("Студенты проверются"
                + hostName + ", " + login + ", " + password);
        return new AnswerStudent();
    }
}

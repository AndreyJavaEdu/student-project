package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerChildren;
import edu.pet_project.studentorder.domain.StudentOrder;

public class ChildrenValidator {
    String hostName;
    String login;
    String password;

     public AnswerChildren chekChildren(StudentOrder so){
        System.out.println("Children Check is running"
                + hostName + ", " + login + ", " + password);
        return new AnswerChildren();
    }
}

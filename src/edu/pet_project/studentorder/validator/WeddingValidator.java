package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerWedding;
import edu.pet_project.studentorder.domain.StudentOrder;

public class WeddingValidator {

    String hostName;
    String login;
    String password;

    public AnswerWedding checkWedding(StudentOrder so) {
        System.out.println("Wedding запущен: "
                + hostName + ", " + login + ", " + password);
        AnswerWedding ans = new AnswerWedding();
        return ans;
    }

}

package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerCityRegister;
import edu.pet_project.studentorder.domain.StudentOrder;

public class CityRegisterValidator {

    public String hostName;
    String login;
    String password;

     public AnswerCityRegister checkCityRegister(StudentOrder so){
        System.out.println("CityRegister is running:"
                + hostName +", "+login+ ", "+password);
        AnswerCityRegister ans = new AnswerCityRegister();
        ans.succsess = false;
        return ans;
    }
}

package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.exception.CityRegisterException;

import java.util.List;

public class CityRegisterValidator {

//    public String hostName;
//    protected int port;
//    private String login;
//    String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {

        personChecker = new FakeCityRegisterChecker();
    }


    public AnswerCityRegister checkCityRegister(StudentOrder so) {
            AnswerCityRegister ans = new AnswerCityRegister();
            ans.addItem(checkPerson(so.getHusband())); // в студ заявлении есть муж
            ans.addItem(checkPerson(so.getWife()));
            List<Child> children = so.getChildren();
            // Способ for each loop
            for (Child child: children) {
                ans.addItem(checkPerson(child));
            }

        return ans;
    }

    private AnswerCityRegisterItem checkPerson (Person person){
        try {
            CityRegisterResponse cans = personChecker.checkPerson(person);
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }
}

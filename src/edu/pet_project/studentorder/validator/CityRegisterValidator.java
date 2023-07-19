package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerCityRegister;
import edu.pet_project.studentorder.domain.Child;
import edu.pet_project.studentorder.domain.CityRegisterResponse;
import edu.pet_project.studentorder.domain.StudentOrder;
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
        try {
            CityRegisterResponse hans = personChecker.checkPerson(so.getHusband()); // в студ заявлении есть муж
            CityRegisterResponse wans = personChecker.checkPerson(so.getWife());
            //1 способ пройтись по всем элементам по номеру
            List<Child> children = so.getChildren();
            // Способ for each loop
            for (Child child: children) {
                CityRegisterResponse cans = personChecker.checkPerson(child);
            }
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
        }

        AnswerCityRegister ans = new AnswerCityRegister();
        return ans;
    }
}

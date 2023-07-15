package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerCityRegister;
import edu.pet_project.studentorder.domain.StudentOrder;
import edu.pet_project.studentorder.exception.CityRegisterException;

public class CityRegisterValidator {

    public String hostName;
    protected int port;
    private String login;
    String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }



    public AnswerCityRegister checkCityRegister(StudentOrder so){
        try {
            personChecker.checkPerson(so.getHusband()); // в студ заявлении есть муж
            personChecker.checkPerson(so.getWife());
            personChecker.checkPerson(so.getChild());
        } catch (CityRegisterException ex){
            ex.printStackTrace();
        }


        AnswerCityRegister ans = new AnswerCityRegister();

        return ans;
    }
}

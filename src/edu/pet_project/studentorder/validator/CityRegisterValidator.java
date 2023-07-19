package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.AnswerCityRegister;
import edu.pet_project.studentorder.domain.Child;
import edu.pet_project.studentorder.domain.CityRegisterCheckerResponse;
import edu.pet_project.studentorder.domain.StudentOrder;
import edu.pet_project.studentorder.exception.CityRegisterException;

import java.util.Iterator;
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
            CityRegisterCheckerResponse hans = personChecker.checkPerson(so.getHusband()); // в студ заявлении есть муж
            CityRegisterCheckerResponse wans = personChecker.checkPerson(so.getWife());
            //1 способ пройтись по всем элементам по номеру
            List<Child> children = so.getChildren();
            for(int i = 0; i< children.size(); i++) {
                CityRegisterCheckerResponse cans = personChecker.checkPerson(children.get(i));
            }
            //2 способ пробежаться по коллекции с помощью Итератора
            for (Iterator<Child> it = children.iterator(); it.hasNext(); ){
                Child child = it.next();
                CityRegisterCheckerResponse cans = personChecker.checkPerson(child);
            }
            //3 способ for each loop
            for (Child child: children) {
                CityRegisterCheckerResponse cans = personChecker.checkPerson(child);
            }
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
        }

        AnswerCityRegister ans = new AnswerCityRegister();
        return ans;
    }
}

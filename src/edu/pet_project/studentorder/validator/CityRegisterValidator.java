package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.exception.CityRegisterException;
import edu.pet_project.studentorder.exception.TransportException;

import java.util.List;

public class CityRegisterValidator {

public static final String IN_CODE = "NO_GRN";

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
        AnswerCityRegisterItem.CityStatus status=null;
        AnswerCityRegisterItem.CityError error=null;
        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isExisting() ?
                    AnswerCityRegisterItem.CityStatus.YES:
                    AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        }catch (TransportException ex) {
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }
        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);
        return ans;
    }
}

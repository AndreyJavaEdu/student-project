package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.Adult;
import edu.pet_project.studentorder.domain.Child;
import edu.pet_project.studentorder.domain.CityRegisterCheckerResponse;
import edu.pet_project.studentorder.domain.Person;
import edu.pet_project.studentorder.exception.CityRegisterException;

public class FakeCityRegisterChecker implements CityRegisterChecker  {
    public CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException {
        if (person instanceof Adult) {
            Adult t = (Adult) person; //приведение
        }
        return null;
    }
}

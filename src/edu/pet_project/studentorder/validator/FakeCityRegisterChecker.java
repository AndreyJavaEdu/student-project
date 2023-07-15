package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.CityRegisterCheckerResponse;
import edu.pet_project.studentorder.domain.Person;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    public CityRegisterCheckerResponse checkPerson(Person person){
        return null;
    }
}

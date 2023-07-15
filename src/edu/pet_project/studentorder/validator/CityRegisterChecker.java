package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.CityRegisterCheckerResponse;
import edu.pet_project.studentorder.domain.Person;

public interface CityRegisterChecker {
     CityRegisterCheckerResponse checkPerson(Person person);
}

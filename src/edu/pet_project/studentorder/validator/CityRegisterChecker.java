package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.CityRegisterCheckerResponse;
import edu.pet_project.studentorder.domain.Person;
import edu.pet_project.studentorder.exception.CityRegisterException;

public interface CityRegisterChecker {
     CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException;
}

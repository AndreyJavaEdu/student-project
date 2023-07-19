package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.CityRegisterResponse;
import edu.pet_project.studentorder.domain.Person;
import edu.pet_project.studentorder.exception.CityRegisterException;

public interface CityRegisterChecker {
     CityRegisterResponse checkPerson(Person person) throws CityRegisterException;
}

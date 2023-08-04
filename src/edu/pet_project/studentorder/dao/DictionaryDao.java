package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.domain.CountryArea;
import edu.pet_project.studentorder.domain.PassportOffice;
import edu.pet_project.studentorder.domain.RegisterOffice;
import edu.pet_project.studentorder.domain.Street;
import edu.pet_project.studentorder.exception.DaoException;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreets(String pattern) throws DaoException;
    List<PassportOffice> findPassportOffices(String areaId) throws DaoException;
    List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException;
    List<CountryArea> findAreas(String areaId) throws DaoException;


}

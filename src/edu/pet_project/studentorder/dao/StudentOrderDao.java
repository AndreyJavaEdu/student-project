package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.domain.StudentOrder;
import edu.pet_project.studentorder.exception.DaoException;

public interface StudentOrderDao {
    Long saveStudentOrder(StudentOrder so) throws DaoException;
}

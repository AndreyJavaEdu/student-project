package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.domain.StudentOrder;
import edu.pet_project.studentorder.exception.DaoException;

import java.util.List;

public interface StudentOrderDao {
    Long saveStudentOrder(StudentOrder so) throws DaoException;
    List<StudentOrder>getStudentOrders() throws DaoException;
}

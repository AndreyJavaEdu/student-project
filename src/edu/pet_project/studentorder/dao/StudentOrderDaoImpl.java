package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.config.Config;
import edu.pet_project.studentorder.domain.StudentOrder;
import edu.pet_project.studentorder.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentOrderDaoImpl implements StudentOrderDao {


    // TODO - makee one method
    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }
    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        return null;
    }
}

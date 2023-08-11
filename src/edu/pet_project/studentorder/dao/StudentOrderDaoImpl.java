package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.config.Config;
import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.exception.DaoException;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao {
    public static final String INSERT_ORDER =
            "INSERT INTO public.jc_student_order(" +
                    "student_order_status, student_order_date, h_surname, h_given_name," +
                    " h_patronomyc, h_date_of_birth, h_pasport_seria, h_passport_number, h_passport_date," +
                    " h_passport_office_id, h_post_index, h_street_code, h_building, h_extension, h_apartment," +
                    " w_surname, w_given_name, w_patronomyc, w_date_of_birth, w_pasport_seria, w_passport_number," +
                    " w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension," +
                    " w_apartment, certificate_id, register_office_id, marriage_date)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


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
        Long result =-1L;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            //Header
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now())); //текущая дата

            //Husband and Wife - вызов метода
            setParamsForAdult(stmt, 3, so.getHusband());
            setParamsForAdult(stmt, 16, so.getWife());

            //Marriage - сведения о регистрации брака
            stmt.setString(29, so.getMarriageCertificateId());
            stmt.setLong(30, so.getMarriageOffice().getOfficeId());
            stmt.setDate(31, java.sql.Date.valueOf(so.getMarriageDate()));

            stmt.executeUpdate();
            ResultSet gkRs =  stmt.getGeneratedKeys();
            if (gkRs.next()){
                result = gkRs.getLong(1);
            }
            gkRs.close();

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        stmt.setString(start, adult.getSurname());
        stmt.setString(start +1, adult.getGivenName());
        stmt.setString(start +2, adult.getPatronomyc());
        stmt.setDate(start +3, Date.valueOf(adult.getDateOfBirthday()));
        stmt.setString(start +4, adult.getPassportSerial());
        stmt.setString(start +5, adult.getPassportNumber());
        stmt.setDate(start +6, Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start +7, adult.getIssueDepartment().getOfficeId());
        Address h_adress = adult.getAddress();
        stmt.setString(start +8, h_adress.getPostCode());
        stmt.setLong(start +9, h_adress.getStreet().getStreetCode());
        stmt.setString(start +10, h_adress.getBuilding());
        stmt.setString(start +11, h_adress.getExtension());
        stmt.setString(start +12, h_adress.getApartment());
    }
}


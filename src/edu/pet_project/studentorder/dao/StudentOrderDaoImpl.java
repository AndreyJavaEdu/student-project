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


            Adult husband = so.getHusband();
            stmt.setString(3, so.getHusband().getSurname());
            stmt.setString(4, so.getHusband().getGivenName());
            stmt.setString(5, so.getHusband().getPatronomyc());
            stmt.setDate(6, java.sql.Date.valueOf(so.getHusband().getDateOfBirthday()));
            stmt.setString(7, so.getHusband().getPassportSerial());
            stmt.setString(8, so.getHusband().getPassportNumber());
            stmt.setDate(9, java.sql.Date.valueOf(so.getHusband().getIssueDate()));
            stmt.setLong(10, so.getHusband().getIssueDepartment().getOfficeId());
            Address h_adress = husband.getAddress();
            stmt.setString(11, h_adress.getPostCode());
            stmt.setLong(12, h_adress.getStreet().getStreetCode());
            stmt.setString(13, h_adress.getBuilding());
            stmt.setString(14, h_adress.getExtension());
            stmt.setString(15, h_adress.getApartment());

            //Wife
            Adult wife = so.getWife();
            stmt.setString(16, so.getWife().getSurname());
            stmt.setString(17, so.getWife().getGivenName());
            stmt.setString(18, so.getWife().getPatronomyc());
            stmt.setDate(19, java.sql.Date.valueOf(so.getWife().getDateOfBirthday()));
            stmt.setString(20, so.getWife().getPassportSerial());
            stmt.setString(21, so.getWife().getPassportNumber());
            stmt.setDate(22, java.sql.Date.valueOf(so.getWife().getIssueDate()));
            stmt.setLong(23, so.getWife().getIssueDepartment().getOfficeId());
            Address w_adress = wife.getAddress();
            stmt.setString(24, w_adress.getPostCode());
            stmt.setLong(25, w_adress.getStreet().getStreetCode());
            stmt.setString(26, w_adress.getBuilding());
            stmt.setString(27, w_adress.getExtension());
            stmt.setString(28, w_adress.getApartment());

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
}


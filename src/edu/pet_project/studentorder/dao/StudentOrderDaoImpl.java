package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.config.Config;
import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.exception.DaoException;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao {
    private static final String INSERT_ORDER =
            "INSERT INTO public.jc_student_order(" +
                    "student_order_status, student_order_date, h_surname, h_given_name," +
                    " h_patronomyc, h_date_of_birth, h_pasport_seria, h_passport_number, h_passport_date," +
                    " h_passport_office_id, h_post_index, h_street_code, h_building, h_extension, h_apartment," +
                    " w_surname, w_given_name, w_patronomyc, w_date_of_birth, w_pasport_seria, w_passport_number," +
                    " w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension," +
                    " w_apartment, certificate_id, register_office_id, marriage_date)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_CHILD =
            "INSERT INTO public.jc_student_child" +
                    "(student_order_id, с_surname, с_gсiven_name, с_patronomyc, с_date_of_birth, с_certificate_number, " +
                    "с_certificate_date, с_register_office_id, с_post_index, " +
                    "с_street_code, с_building, с_extension, с_apartment)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


    // TODO - make one method
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

            saveChildren(con, so, result);

        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException {
        try(PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1, soId);
                setParamsForChild(stmt, child);
                stmt.executeUpdate();

            }
        }
    }



    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start +4, adult.getPassportSerial());
        stmt.setString(start +5, adult.getPassportNumber());
        stmt.setDate(start +6, java.sql.Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start +7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, start + 8, adult);
    }
    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, 9, child);
    }
    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSurname());
        stmt.setString(start +1, person.getGivenName());
        stmt.setString(start +2, person.getPatronomyc());
        stmt.setDate(start +3, java.sql.Date.valueOf(person.getDateOfBirthday()));
    }
    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address h_adress = person.getAddress();
        stmt.setString(start, h_adress.getPostCode());
        stmt.setLong(start +1, h_adress.getStreet().getStreetCode());
        stmt.setString(start +2, h_adress.getBuilding());
        stmt.setString(start +3, h_adress.getExtension());
        stmt.setString(start +4, h_adress.getApartment());
    }
}


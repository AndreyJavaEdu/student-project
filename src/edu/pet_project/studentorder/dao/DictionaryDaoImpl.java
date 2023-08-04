package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.config.Config;
import edu.pet_project.studentorder.domain.CountryArea;
import edu.pet_project.studentorder.domain.PassportOffice;
import edu.pet_project.studentorder.domain.RegisterOffice;
import edu.pet_project.studentorder.domain.Street;
import edu.pet_project.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
//выделение отдельного метода дл получения коннекта


public class DictionaryDaoImpl implements DictionaryDao {

   public static final String GET_STREET = "select street_code, street_name from jc_street where UPPER(street_name) like UPPER(?)";
   public static final String GET_PASSPORT = "select * " +
            "from jc_passport_office where p_office_area_id = ?";
   public static final String GET_REGISTER = "select * " +
            "from jc_register_office where r_office_area_id = ?";
    public static final String GET_AREA= "select * " +
            "from jc_country_struct where area_id like ? AND area_id <> ? ";

    // TODO - makee one method
    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street>result = new LinkedList<>();

        try( Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_STREET)) {
            stmt.setString(1, "%"+pattern+"%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Street str = new Street(rs.getLong("street_code"), rs.getString("street_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {

        List<PassportOffice>result = new LinkedList<>();

        try( Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_PASSPORT)) {
            stmt.setString(1, areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PassportOffice str = new PassportOffice(
                        rs.getLong("p_office_id"),
                        rs.getString("p_office_area_id"),
                        rs.getString("p_office_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException {
        List<RegisterOffice>result = new LinkedList<>();

        try( Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_REGISTER)) {
            stmt.setString(1, areaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegisterOffice str = new RegisterOffice(
                        rs.getLong("r_office_id"),
                        rs.getString("r_office_area_id"),
                        rs.getString("r_office_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea>result = new LinkedList<>();

        try( Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_AREA)) {

            String param1 = buildParam(areaId);
            String param2 = areaId;
            stmt.setString(1, param1);
            stmt.setString(2, param2);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CountryArea str = new CountryArea(
                        rs.getString("area_id"),
                        rs.getString("area_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    private String buildParam(String areaId) throws SQLException {
        if (areaId == null || areaId.trim().isEmpty()) {
            return "__0000000000";
        } else if (areaId.endsWith("0000000000")) {
            return areaId.substring(0, 2) + "___0000000";
        } else if (areaId.endsWith("0000000")) {
            return areaId.substring(0, 5) + "___0000";
        } else if (areaId.endsWith("0000")) {
            return areaId.substring(0, 8) + "____";
        }
        throw new SQLException("Invalid parameter 'areaId': " + areaId);
    }
}

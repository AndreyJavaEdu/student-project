package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.config.Config;
import edu.pet_project.studentorder.domain.Street;
import edu.pet_project.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
//выделение отдельного метода дл получения коннекта


public class DictionaryDaoImpl implements DictionaryDao {

   public static final String GET_STREET = "select street_code, street_name from jc_street where UPPER(street_name) like UPPER(?)";

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
}

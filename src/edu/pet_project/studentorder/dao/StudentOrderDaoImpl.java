package edu.pet_project.studentorder.dao;

import edu.pet_project.studentorder.config.Config;
import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.exception.DaoException;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentOrderDaoImpl implements StudentOrderDao {
    private static final String INSERT_ORDER =
            "INSERT INTO public.jc_student_order(" +
                    "student_order_status, student_order_date, h_surname, h_given_name," +
                    " h_patronomyc, h_date_of_birth, h_pasport_seria, h_passport_number, h_passport_date," +
                    " h_passport_office_id, h_post_index, h_street_code, h_building, " +
                    "h_extension, h_apartment, h_university_id, h_student_number, w_surname, w_given_name, w_patronomyc," +
                    " w_date_of_birth, w_pasport_seria, w_passport_number," +
                    " w_passport_date, w_passport_office_id, w_post_index, w_street_code, " +
                    "w_building, w_extension, w_apartment, w_university_id, w_student_number,  " +
                    "certificate_id, register_office_id, marriage_date)" +
                    "VALUES (?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?," +
                    "?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?," +
                    " ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?," +
                    "?, ?, ?);";
    private static final String INSERT_CHILD =
            "INSERT INTO public.jc_student_child" +
                    "(student_order_id, с_surname, с_gсiven_name, с_patronomyc, с_date_of_birth, с_certificate_number, " +
                    "с_certificate_date, с_register_office_id, с_post_index, " +
                    "с_street_code, с_building, с_extension, с_apartment)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ORDERS =
                "SELECT ro.r_office_area_id, ro.r_office_name, so.*, " +
                        "h_jpo.p_office_area_id as h_p_office_area_id , h_jpo.p_office_name as h_p_office_name, " +
                        "w_jpo.p_office_area_id as w_p_office_area_id, w_jpo.p_office_name as w_p_office_name " +
                        "FROM jc_student_order so " +
                        "inner join jc_register_office ro on (so.register_office_id = ro.r_office_id) " +
                        "inner join jc_passport_office h_jpo on h_passport_office_id = h_jpo.p_office_id " +
                        "inner join jc_passport_office w_jpo on w_passport_office_id =w_jpo.p_office_id  " +
                        "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?";

    private static final String SELECT_CHILD =
            "SELECT soc.*, ro.r_office_area_id, ro.r_office_name " +
            "FROM jc_student_child soc " +
            "INNER JOIN jc_register_office ro on (ro.r_office_id = soc.с_register_office_id) " +
            "WHERE soc.student_order_id in ";

    private static final String SELECT_ORDERS_FULL =
            "SELECT ro.r_office_area_id, ro.r_office_name, so.*, " +
                    "h_jpo.p_office_area_id as h_p_office_area_id , h_jpo.p_office_name as h_p_office_name, " +
                    "w_jpo.p_office_area_id as w_p_office_area_id, w_jpo.p_office_name as w_p_office_name, " +
                    "soc.*, ro_c.r_office_area_id, ro_c.r_office_name "+
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro on (so.register_office_id = ro.r_office_id) " +
                    "INNER JOIN  jc_passport_office h_jpo on (h_passport_office_id = h_jpo.p_office_id) " +
                    "INNER JOIN  jc_passport_office w_jpo on (w_passport_office_id = w_jpo.p_office_id)  " +
                    "INNER JOIN  jc_student_child soc on (soc.student_order_id = so.student_order_id) "+
                    "INNER JOIN jc_register_office ro_c on (soc.с_register_office_id = ro_c.r_office_id) " +
                    "WHERE student_order_status = ? ORDER BY so.student_order_id LIMIT ?";


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
            con.setAutoCommit(false);
            try {
                //Header
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now())); //текущая дата

                //Husband and Wife - вызов метода
                setParamsForAdult(stmt, 3, so.getHusband());
                setParamsForAdult(stmt, 18, so.getWife());

                //Marriage - сведения о регистрации брака
                stmt.setString(33, so.getMarriageCertificateId());
                stmt.setLong(34, so.getMarriageOffice().getOfficeId());
                stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));

                stmt.executeUpdate();
                ResultSet gkRs = stmt.getGeneratedKeys();
                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }
                gkRs.close();
                saveChildren(con, so, result);

                con.commit();
            } catch (SQLException ex){
                con.rollback();
                throw ex;
            }

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
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start +4, adult.getPassportSerial());
        stmt.setString(start +5, adult.getPassportNumber());
        stmt.setDate(start +6, java.sql.Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start +7, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, start + 8, adult);
        stmt.setLong(start +13, adult.getUniversity().getUniversityId());
        stmt.setString(start + 14, adult.getStudentId());
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
        Address adult_adress = person.getAddress();
        stmt.setString(start, adult_adress.getPostCode());
        stmt.setLong(start +1, adult_adress.getStreet().getStreetCode());
        stmt.setString(start +2, adult_adress.getBuilding());
        stmt.setString(start +3, adult_adress.getExtension());
        stmt.setString(start +4, adult_adress.getApartment());
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        return getStudentOrdersOneSelect();
//        return getStudentOrdersTwoSelect();
    }
    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS_FULL)){
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            stmt.setInt(2, limit);

            Map<Long, StudentOrder> maps = new HashMap<>();
            ResultSet rs = stmt.executeQuery();
            int counter = 0;
            while (rs.next()) {
                Long soId = rs.getLong("student_order_id");
                if (!maps.containsKey(soId)) { // тут проверяется есть ли у нас такая студенческая заявка вообще
                    StudentOrder so =getFullStudentOrder(rs);

                    result.add(so);
                    maps.put(soId, so);
                }
                StudentOrder so = maps.get(soId);
                so.addChild(fillChild(rs));
                counter++;
            }
            if(counter >= limit){
               result.remove(result.size()-1); //удаляем последнюю записанную заявку из коллекции LinkedList
            }
            rs.close();
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }
    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)){
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            stmt.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                StudentOrder so = getFullStudentOrder(rs);

                result.add(so);
            }
            findChildren(con, result);
            rs.close();
        }catch (SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }
        // создаем студ заявку
    private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
            StudentOrder so = new StudentOrder();

            fillStudentOrder(rs, so);
            fillMarriage(rs, so);

            so.setHusband(fillAdult(rs, "h_"));
            so.setWife(fillAdult(rs, "w_"));
            return so;
        }


    private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
        so.setStudentOrderId(rs.getLong("student_order_id"));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
    }
    private Adult fillAdult(ResultSet rs, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSurname(rs.getString(pref+"surname"));
        adult.setGivenName(rs.getString(pref+"given_name"));
        adult.setPatronomyc(rs.getString(pref+"patronomyc"));

        adult.setDateOfBirthday(rs.getDate(pref+"date_of_birth").toLocalDate());
        adult.setPassportSerial(rs.getString(pref+"pasport_seria"));
        adult.setPassportNumber(rs.getString(pref+"passport_number"));
        adult.setIssueDate(rs.getDate(pref+"passport_date").toLocalDate());

        Long pOfficeId = rs.getLong(pref + "passport_office_id");
        String pAreaId = rs.getString(pref+"p_office_area_id");
        String pOfficeName = rs.getString(pref + "p_office_name");
        PassportOffice po = new PassportOffice(pOfficeId, pAreaId, pOfficeName);
        adult.setIssueDepartment(po);

        Address adr = new Address();
        adr.setPostCode(rs.getString(pref+"post_index"));
        adr.setBuilding(rs.getString(pref + "building"));
        adr.setExtension(rs.getString(pref + "extension"));
        adr.setApartment(rs.getString(pref + "apartment"));
        Street st = new Street(rs.getLong(pref + "street_code"), "");
        adr.setStreet(st);
        adult.setAddress(adr);

        University uni = new University();
        uni.setUniversityId(rs.getLong(pref + "university_id"));
        uni.setUniversityName("");
        adult.setUniversity(uni);
        adult.setStudentId(rs.getString(pref+ "student_number"));

        return adult;
    }
    private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
        so.setMarriageCertificateId(rs.getString("certificate_id"));
        so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());

        Long roId = rs.getLong("register_office_id");
        String areaId = rs.getString("r_office_area_id");
        String name = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, areaId, name);
        so.setMarriageOffice(ro);
    }
    private void findChildren(Connection con, List<StudentOrder> result) throws SQLException {
        //сформировали прибавочный кусочек для Селекта
        String cl = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderId())).
                collect(Collectors.joining(", ")) + ")";
        System.out.println(cl);

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors.toMap(so -> so.getStudentOrderId(), so -> so)); // перевели LinkedList в Map

        try (PreparedStatement stmt = con.prepareStatement(SELECT_CHILD + cl)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Child ch = fillChild(rs);
                StudentOrder so = maps.get(rs.getLong("student_order_id"));
                so.addChild(ch);

            }
            rs.close();

        }
    }
    private Child fillChild(ResultSet rs) throws SQLException {
        String surName = rs.getString("с_surname");
        String givenName = rs.getString("с_gсiven_name");
        String patronomic = rs.getString("с_patronomyc");
        LocalDate birth = rs.getDate("с_date_of_birth").toLocalDate();

        Child child = new Child(surName, givenName, patronomic, birth);

        child.setCertificateNumber(rs.getString("с_certificate_number"));
        child.setIssueDate(rs.getDate("с_certificate_date").toLocalDate());
        RegisterOffice ro = new RegisterOffice(rs.getLong("с_register_office_id"),
                rs.getString("r_office_area_id"),
                rs.getString("r_office_name"));
        child.setIssueDepartment(ro);

        Address adr = new Address();
        Street st = new Street(rs.getLong("с_street_code"), "");
        adr.setStreet(st);
        adr.setPostCode(rs.getString("с_post_index"));
        adr.setBuilding(rs.getString("с_building"));
        adr.setExtension(rs.getString("с_extension"));
        adr.setApartment(rs.getString("с_apartment"));
        child.setAddress(adr);


        return child;
    }
}


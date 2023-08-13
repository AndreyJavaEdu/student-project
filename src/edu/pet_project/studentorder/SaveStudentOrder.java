package edu.pet_project.studentorder;

import edu.pet_project.studentorder.dao.DictionaryDao;
import edu.pet_project.studentorder.dao.DictionaryDaoImpl;
import edu.pet_project.studentorder.dao.StudentOrderDao;
import edu.pet_project.studentorder.dao.StudentOrderDaoImpl;
import edu.pet_project.studentorder.domain.*;
import edu.pet_project.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class    SaveStudentOrder {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, DaoException {
//        List<Street> d = new DictionaryDaoImpl().findStreets("про");
//        for (Street s : d){
//            System.out.println(s.getStreetName());
//        }
//
//        List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
//        for (PassportOffice p: po) {
//            System.out.println(p.getOfficeName());
//        }
//
//        List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices("010010000000");
//        for (RegisterOffice r: ro) {
//            System.out.println(r.getOfficeName());
//        }
//        List<CountryArea> countryArea1 = new DictionaryDaoImpl().findAreas("");
//        for (CountryArea c: countryArea1) {
//            System.out.println(c.getAreaId() + ":" + c.getAreaName());
//        }
//        System.out.println("--->");
//        List<CountryArea> countryArea2 = new DictionaryDaoImpl().findAreas("020000000000");
//        for (CountryArea c: countryArea2) {
//            System.out.println(c.getAreaId() + ":" + c.getAreaName());
//        }
//        System.out.println("--->");
//        List<CountryArea> countryArea3 = new DictionaryDaoImpl().findAreas("020010000000");
//        for (CountryArea c: countryArea3) {
//            System.out.println(c.getAreaId() + ":" + c.getAreaName());
//        }
//        System.out.println("--->");
//        List<CountryArea> countryArea4 = new DictionaryDaoImpl().findAreas("020010010000");
//        for (CountryArea c: countryArea4) {
//            System.out.println(c.getAreaId() + ":" + c.getAreaName());
//        }

//        StudentOrder s = buildStudentOrder(10);
        StudentOrderDao dao = new StudentOrderDaoImpl();
//        Long id = dao.saveStudentOrder(s);
//        System.out.println(id);
        
        //Проверяем данные который вытаскиваем SQL Селектом
        List<StudentOrder> soList = dao.getStudentOrders();
        for (StudentOrder so: soList) {
            System.out.println(so.getStudentOrderId());

        }


//        StudentOrder so = new StudentOrder();
//        long ans = saveStudentOrder(so);
//        System.out.println(ans);
    }
    static long saveStudentOrder(StudentOrder studentOrder ){
        long answer=199;
        System.out.println("saveStudentOrder");
        return answer;
    }

    public static StudentOrder buildStudentOrder(long id){
        StudentOrder so = new StudentOrder();
         so.setStudentOrderId(id);
         so.setMarriageCertificateId("" + (123456+id));
         so.setMarriageDate(LocalDate.of(2016, 7, 4));
         RegisterOffice ro = new RegisterOffice(1L, "", "");
         so.setMarriageOffice(ro);

         Street street = new Street(1L, "First Street");

        Address address = new Address("195000", street, "12", "", "142");

        //Муж
         Adult husband = new Adult("Васильев", "Андрей", "Петрович", LocalDate.of(1997, 8, 24));
         husband.setPassportSerial("" + (1000 + id));
         husband.setPassportNumber("" +100000 + id );
         husband.setIssueDate(LocalDate.of(2017, 9, 15));
         PassportOffice po1 = new PassportOffice(1L, "", "");
         husband.setIssueDepartment(po1);
         husband.setStudentId("" + (1000 + id));
         husband.setAddress(address);
         husband.setUniversity(new University(2L, ""));
         husband.setStudentId("HH12345");
//        System.out.println();

         //Жена
        Adult wife = new Adult("Петрова", "Вероника", "Алексеевна", LocalDate.of(1998, 3, 12));
        wife.setPassportSerial("" + (2000+id));
        wife.setPassportNumber(""+(200000+id));
        wife.setIssueDate(LocalDate.of(2018, 4, 5));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(po2);
        wife.setStudentId("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, ""));
        wife.setStudentId("WW12345");


        //Ребенок
        Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 5, 12));
        child1.setCertificateNumber("" + (300000+id));
        child1.setIssueDate(LocalDate.of(2018, 6, 11));
        RegisterOffice ro2 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        //Ребенок
        Child child2 = new Child("Петров", "Евгений", "Викторович", LocalDate.of(2018, 4, 29));
        child2.setCertificateNumber("" + (400000+id));
        child2.setIssueDate(LocalDate.of(2018, 7, 19));
        RegisterOffice ro3 = new RegisterOffice(3L, "", "");
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);


        return so;
    }

//    static void printStudentOrder(StudentOrder stOr){
//        System.out.println(stOr.getStudentOrderId());
//    }
}


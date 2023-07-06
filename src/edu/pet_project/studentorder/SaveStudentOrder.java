package edu.pet_project.studentorder;

import edu.pet_project.studentorder.domain.Adult;
import edu.pet_project.studentorder.domain.Child;
import edu.pet_project.studentorder.domain.Person;
import edu.pet_project.studentorder.domain.StudentOrder;

public class SaveStudentOrder {

    public static void main(String[] args) {
        buildStudentOrder();
//        StudentOrder so = new StudentOrder();
//        long ans = saveStudentOrder(so);
//        System.out.println(ans);
    }
    static long saveStudentOrder(StudentOrder studentOrder ){
        long answer=199;
        System.out.println("saveStudentOrder");
        return answer;
    }

    static StudentOrder buildStudentOrder(){
        StudentOrder so = new StudentOrder();
        Adult husband = new Adult();

//        Person p = new Person();
//        husband.setGivenName("Андрей");
//        husband.setSurname("Kamenski");
//        husband.setPassportNumber("12324");
//        so.setHusband(husband);
//
//        String ans =  husband.getPersonString();
        System.out.println(ans);


        return so;
    }
}


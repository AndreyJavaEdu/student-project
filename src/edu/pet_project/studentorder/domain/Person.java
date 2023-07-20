package edu.pet_project.studentorder.domain;

import java.time.LocalDate;

/*Обобщенная структура, адрес является частью Персоны через
через переменную типа Address и класс Address, где
общие поля адреса
 */
public abstract class Person {
   private String surname;
   private String givenName;
   private String patronomyc;//Отчество
   private LocalDate dateOfBirthday;
   private Address address;

   public Person(){}

   public Person(String surname, String givenName, String patronomyc, LocalDate dateOfBirthday) {
      this.surname = surname;
      this.givenName = givenName;
      this.patronomyc = patronomyc;
      this.dateOfBirthday = dateOfBirthday;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public String getGivenName() {
      return givenName;
   }

   public void setGivenName(String givenName) {
      this.givenName = givenName;
   }

   public String getPatronomyc() {
      return patronomyc;
   }

   public void setPatronomyc(String patronomyc) {
      this.patronomyc = patronomyc;
   }

   public LocalDate getDateOfBirthday() {
      return dateOfBirthday;
   }

   public void setDateOfBirthday(LocalDate dateOfBirthday) {
      this.dateOfBirthday = dateOfBirthday;
   }
}

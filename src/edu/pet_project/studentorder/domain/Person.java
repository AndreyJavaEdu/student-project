package edu.pet_project.studentorder.domain;

import java.time.LocalDate;

/*Обобщенная структура, адрес является частью Персоны через
через переменную типа Address и класс Address, где
общие поля адреса
 */
public abstract class Person {
   protected String surname;
   protected String givenName;
   private String patronomyc;//Отчество
   private LocalDate dateOfBithday;
   private Address address;

   public String getPersonString(){
      return surname+ " " + givenName;
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

   public LocalDate getDateOfBithday() {
      return dateOfBithday;
   }

   public void setDateOfBithday(LocalDate dateOfBithday) {
      this.dateOfBithday = dateOfBithday;
   }
}

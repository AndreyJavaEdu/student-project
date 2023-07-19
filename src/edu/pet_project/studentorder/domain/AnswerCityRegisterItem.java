package edu.pet_project.studentorder.domain;

public class AnswerCityRegisterItem {

    public enum CityStatus{
        YES, NO, ERROR; //зарегистрирован, незарегистрирован, произошла ошибка
    }

    //Создаем вложенный класс дл ошибки
    public static class CityError{
        private String code;
        private String text;

        public CityError(String code, String text) {
            this.code = code;
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public String getText() {
            return text;
        }
    }

    private CityStatus cityStatus; // к этому полю можно присвоить значения из enum, а другие нельзя
    private Person person; //мы проверли такую то персону
    private CityError error;

    public AnswerCityRegisterItem(CityStatus cityStatus, Person person) {
        this.cityStatus = cityStatus;
        this.person = person;
    }

    public AnswerCityRegisterItem(CityStatus cityStatus, Person person, CityError error) {
        this.cityStatus = cityStatus;
        this.person = person;
        this.error = error;
    }

    public CityStatus getCityStatus() {
        return cityStatus;
    }

    public Person getPerson() {
        return person;
    }

    public CityError getError() {
        return error;
    }
}

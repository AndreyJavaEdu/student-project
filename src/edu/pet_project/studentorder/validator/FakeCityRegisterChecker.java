package edu.pet_project.studentorder.validator;

import edu.pet_project.studentorder.domain.Adult;
import edu.pet_project.studentorder.domain.Child;
import edu.pet_project.studentorder.domain.CityRegisterResponse;
import edu.pet_project.studentorder.domain.Person;
import edu.pet_project.studentorder.exception.CityRegisterException;
import edu.pet_project.studentorder.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    private static final String GOOD_1 = "1000";
    private static final String GOOD_2 = "2000";
    private static final String BAD_1 = "1001";
    private static final String BAD_2 = "2001";
    private static final String ERROR_1 = "1002";
    private static final String ERROR_2 = "2002";
    private static final String ERROR_T_1 = "1003";
    private static final String ERROR_T_2= "2003";

    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        CityRegisterResponse res = new CityRegisterResponse();
        if (person instanceof Adult) {
            Adult t = (Adult) person; //приведение
            String ps = t.getPassportSerial();
            if (t.getPassportSerial().equals(GOOD_1) || t.getPassportSerial().equals(GOOD_2)) {
                res.setExisting(true);
                res.setTemporal(false);


            }
            if (t.getPassportSerial().equals(BAD_1) || t.getPassportSerial().equals(BAD_2)) {
                res.setExisting(false);
            }
            if (t.getPassportSerial().equals(ERROR_1) || t.getPassportSerial().equals(ERROR_2)) {
                CityRegisterException ex =
                        new CityRegisterException("1", "GRN Error " + ps);
                throw ex;
            }

            if (t.getPassportSerial().equals(ERROR_T_1) || t.getPassportSerial().equals(ERROR_T_2)) {
                TransportException ex =
                        new TransportException("Transport Error " + ps);
                throw ex;
            }
        }
            if (person instanceof Child){
                res.setExisting(true);
                res.setTemporal(true);
            }

        System.out.println(res);
        return res;
    }
}

package org.dudar.controller.util;

public class Validator {

    private static Validator instance = new Validator();

    public static Validator getInstance() {
        return instance;
    }

    private Validator() {
    }

    public boolean validatePhoneNumber(String number) {
        String phoneNumberRegex = "^\\+\\d{2}\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$";
        if (number == null || !number.matches(phoneNumberRegex) || number == "") {
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,20}$";
        if (password == null || !password.matches(passwordRegex) || password == "") {
            return false;
        }
        return true;
    }

    public boolean validateId(String id) {
        String idRegex = "^\\d+$";
        if (id == null || !id.matches(idRegex)) {
            return false;
        }
        return true;
    }
}

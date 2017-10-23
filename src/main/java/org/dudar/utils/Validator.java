package org.dudar.utils;

public class Validator {

    private static Validator instance = new Validator();

    public static Validator getInstance() {
        return instance;
    }

    private Validator() {
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^\\+\\d{3}\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}$";
        return validate(phoneNumber, phoneNumberRegex);
    }

    public boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,20}$";
        return validate(password, passwordRegex);
    }

    public boolean validateId(String id) {
        String idRegex = "^\\d+$";
        return validate(id, idRegex);
    }

    public boolean validateName(String name) {
        String nameRegex = "^([А-ЯІЇЄ][а-яіїє]*'?[а-яіїє]+)|([A-Z][a-z]+)$";
        return validate(name, nameRegex);
    }

    public boolean validateIsbn(String Isbn) {
        return true; // TODO: 10/23/17  
    }

    private boolean validate(String str, String strRegex) {
        if (str == null || !str.matches(strRegex)) {
            return false;
        }
        return true;
    }
}

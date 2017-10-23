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

    public boolean validateGenre(String genre) {
        String genreRegex = "^([А-ЯІЇЄ][а-яіїє]*'?[а-яіїє]+)|([A-Z][a-z]+)( (([а-яіїє]*'?[а-яіїє]+)|([a-z]+)))*$";
        return validate(genre, genreRegex);
    }

    public boolean validateIsbn(String isbn) {
        String isbnRegex = "\\d{1,3}-(\\d-)?\\d{1,7}-\\d{1,7}-\\d{1,3}";
        return validate(isbn, isbnRegex);
    }

    public boolean validateTitle(String title) {
        String titleRegex = "^.+$";
        return validate(title, titleRegex);
    }

    private boolean validate(String str, String strRegex) {
        if (str == null || !str.matches(strRegex)) {
            return false;
        }
        return true;
    }
}

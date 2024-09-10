package com.workPal.utility.Validation;

import java.util.regex.Pattern;

public class UserValidation {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{4,}$"
    );

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(
            "^[567][0-9]{8}$"
    );

    /**
     * Validates the user's name.
     * @param name The name to validate.
     * @return True if the name is valid, otherwise false.
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Validates the user's email.
     * @param email The email to validate.
     * @return True if the email is valid, otherwise false.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates the user's password.
     * Password must be at least 4 characters long, contain at least one uppercase letter, one lowercase letter, and one digit.
     * @param password The password to validate.
     * @return True if the password is valid, otherwise false.
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Validates the user's phoneNumber.
     * phoneNumber must be
     * @param phoneNumber The phoneNumber to validate.
     * @return True if the phoneNumber is valid, otherwise false.
     */
    public static boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber!= null && PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }



}

package com.workPal.utility.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SubscriptionValidation {



    public static Boolean isDurationTypeValid(String name){
        return name.equals("month") || name.equals("day") || name.equals("week") || name.equals("hour");
    }

    public static  boolean isDateValid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate dateFormated = LocalDate.parse(date, formatter);
            return !dateFormated.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

package ru.digitalleage.gogolev.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private TimeUtils(){};

    public static LocalDate parseDateFromString(String inputString) {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(inputString, formatter);
    }

}

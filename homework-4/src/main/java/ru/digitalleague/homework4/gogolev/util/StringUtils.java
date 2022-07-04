package ru.digitalleague.homework4.gogolev.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework4.gogolev.exceptions.InvalidCommandParameterValueException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Slf4j
@UtilityClass
public class StringUtils {
private static final String  DATE_PATTERN = "dd.MM.yyyy";


    public static Long validateAndGetIdFromString(String idValue) {
        Long id;
        try {
            id = Long.parseLong(idValue);
            if (id < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            log.error("The value of parameter id should be positive integer");
            throw new InvalidCommandParameterValueException("The value of parameter id should be positive integer");
        }
        return id;
    }

    public static LocalDate validateAndParseDateFromString(String inputString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate date;
        try {
            date = LocalDate.parse(inputString, formatter);
        } catch (DateTimeParseException e) {
            log.error("Invalid time pattern. Should be dd.MM.yyyy");
            throw new InvalidCommandParameterValueException("Invalid time pattern. Should be dd.MM.yyyy");
        }
        return date;
    }



}

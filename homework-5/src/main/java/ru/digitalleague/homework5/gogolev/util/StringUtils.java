package ru.digitalleague.homework5.gogolev.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParameterValueException;
import ru.digitalleague.homework5.gogolev.exceptions.InvalidCommandParametersException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class StringUtils {
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    public static boolean validateIdFromString(String idValue) {
        return idValue.matches("\\d+");
    }

    public static Long getIdFromString(String idValue) {
        Long id = -1L;
        try {
            id = Long.parseLong(idValue);
            if (id < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            logParameterValueExc("The value of parameter id should be positive integer");
        }
        return id;
    }

    public static LocalDate validateAndParseDateFromString(String inputString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate date = null;
        try {
            date = LocalDate.parse(inputString, formatter);
        } catch (DateTimeParseException e) {
            logParameterValueExc("Invalid time pattern. Should be dd.MM.yyyy");
        }
        return date;
    }


    public static Map<String, String> getParametersFromRequest(String input) {
        Map<String, String> params = new HashMap<>();
        String[] strings = input.split("_");
        if (strings.length <= 1) {
            logParameterExc("No parameters provided");
        }

        String[] arrayWithParameters = strings[1].split("\\?");
        if (arrayWithParameters.length == 0) {
            logParameterExc("No parameters provided");
        }

        for (String parameter : arrayWithParameters) {
            String key = parameter.split("=")[0];
            String value = parameter.split("=")[1];
            params.put(key, value);
        }

        return params;
    }

    private void logParameterExc(String message) {
        log.error(message);
        throw new InvalidCommandParametersException(message);
    }

    private void logParameterValueExc(String message) {
        log.error(message);
        throw new InvalidCommandParameterValueException(message);
    }

}

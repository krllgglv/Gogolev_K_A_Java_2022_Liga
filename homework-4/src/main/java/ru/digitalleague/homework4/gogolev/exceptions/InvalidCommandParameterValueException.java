package ru.digitalleague.homework4.gogolev.exceptions;

public class InvalidCommandParameterValueException extends RuntimeException {
    public InvalidCommandParameterValueException(String message) {
        super(message);
    }

    public InvalidCommandParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

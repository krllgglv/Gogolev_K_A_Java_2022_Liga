package ru.digitalleague.homework5.gogolev.exceptions;

public class InvalidCommandParameterValueException extends RuntimeException {
    public InvalidCommandParameterValueException(String message) {
        super(message);
    }

    public InvalidCommandParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

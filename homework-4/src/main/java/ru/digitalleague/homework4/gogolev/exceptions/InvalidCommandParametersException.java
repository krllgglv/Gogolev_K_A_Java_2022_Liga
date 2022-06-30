package ru.digitalleague.homework4.gogolev.exceptions;

public class InvalidCommandParametersException extends RuntimeException {
    public InvalidCommandParametersException(String message) {
        super(message);
    }

    public InvalidCommandParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}

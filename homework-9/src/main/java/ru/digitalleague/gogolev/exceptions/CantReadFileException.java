package ru.digitalleague.gogolev.exceptions;

public class CantReadFileException extends RuntimeException {
    public CantReadFileException(String message) {
        super(message);
    }

    public CantReadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

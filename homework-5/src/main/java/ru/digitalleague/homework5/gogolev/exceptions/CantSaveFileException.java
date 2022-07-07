package ru.digitalleague.homework5.gogolev.exceptions;

public class CantSaveFileException extends RuntimeException {
    public CantSaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CantSaveFileException(String message) {
        super(message);
    }
}

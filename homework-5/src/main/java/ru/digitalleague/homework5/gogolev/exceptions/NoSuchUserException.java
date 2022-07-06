package ru.digitalleague.homework5.gogolev.exceptions;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}

package ru.digitalleague.gogolev.exceptions;

public class NoSuchTaskException extends RuntimeException {
    public NoSuchTaskException(String message) {
        super(message);
    }
}

package ru.digitalleague.homework4.gogolev.exceptions;

import java.util.function.Supplier;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}

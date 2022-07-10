package ru.digitalleague.gogolev.exceptions;

public class NoSuchCommandException extends RuntimeException{
    public NoSuchCommandException(String message) {
        super(message);
    }
}

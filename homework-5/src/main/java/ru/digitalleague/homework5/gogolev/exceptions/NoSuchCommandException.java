package ru.digitalleague.homework5.gogolev.exceptions;

public class NoSuchCommandException extends RuntimeException{
    public NoSuchCommandException(String message) {
        super(message);
    }
}

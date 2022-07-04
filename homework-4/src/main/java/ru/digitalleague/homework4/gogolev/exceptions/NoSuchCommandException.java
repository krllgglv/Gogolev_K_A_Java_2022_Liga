package ru.digitalleague.homework4.gogolev.exceptions;

public class NoSuchCommandException extends RuntimeException{
    public NoSuchCommandException(String message) {
        super(message);
    }
}

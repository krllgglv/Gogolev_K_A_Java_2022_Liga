package ru.digitalleague.gogolev.commands;

public interface Command<R> {
    R execute();
}

package ru.digitalleague.homework5.gogolev.commands.factories;

import ru.digitalleague.homework5.gogolev.commands.Command;

public interface CommandsAbstractFactory {
    Command<?> create(String input);
}

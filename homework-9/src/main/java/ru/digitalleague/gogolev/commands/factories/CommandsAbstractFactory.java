package ru.digitalleague.gogolev.commands.factories;

import ru.digitalleague.gogolev.commands.command.Command;

public interface CommandsAbstractFactory {
    Command<?> create(String ... input);
}

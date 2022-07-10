package ru.digitalleague.gogolev.commands.factories;

import ru.digitalleague.gogolev.commands.Command;

public interface CommandsAbstractFactory {
    Command<?> create(String ... input);
}

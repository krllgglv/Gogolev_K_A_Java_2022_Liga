package ru.digitalleage.gogolev.util;

import ru.digitalleage.gogolev.command.Command;

import java.util.Arrays;
import java.util.Optional;

public class CommandUtil {
    private CommandUtil(){}

    public static Optional<Command> getCommandById(Long id) {
        return Arrays.stream(Command.values())
                .filter(c -> c.getCommandId() == id)
                .findFirst();
    }
}

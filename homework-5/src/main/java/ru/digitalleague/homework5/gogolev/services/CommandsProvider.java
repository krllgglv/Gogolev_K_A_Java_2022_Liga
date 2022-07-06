package ru.digitalleague.homework5.gogolev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.EntityCommandsDescription;
import ru.digitalleague.homework5.gogolev.commands.factories.CommandsAbstractFactory;
import ru.digitalleague.homework5.gogolev.commands.factories.EntityCommandsFactory;
import ru.digitalleague.homework5.gogolev.commands.factories.SystemCommandsFactory;
import ru.digitalleague.homework5.gogolev.commands.system_commands.SystemCommandsDescription;
import ru.digitalleague.homework5.gogolev.exceptions.NoSuchCommandException;

import java.util.Arrays;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CommandsProvider {
    private final EntityCommandsFactory entityCommandsFactory;
    private final SystemCommandsFactory systemCommandsFactory;

    public Command<?> provideCommand(String input) {
        String commandName = input.split("_")[0];
        return provideFactory(commandName).create(input);
    }

    private CommandsAbstractFactory provideFactory(String commandName) {
        if (isEntityCommand(commandName.toUpperCase())) {
            return entityCommandsFactory;
        } else if (isSystemCommand(commandName.toUpperCase())) {
            return systemCommandsFactory;
        } else {
            throw new NoSuchCommandException("No such command " + commandName);
        }
    }

    private boolean isSystemCommand(String commandName) {
        return Arrays.stream(SystemCommandsDescription.values())
                .map(Enum::name)
                .collect(Collectors.toSet()).contains(commandName);
    }

    private boolean isEntityCommand(String commandName) {
        return Arrays.stream(EntityCommandsDescription.values())
                .map(Enum::name)
                .collect(Collectors.toSet()).contains(commandName);
    }

}

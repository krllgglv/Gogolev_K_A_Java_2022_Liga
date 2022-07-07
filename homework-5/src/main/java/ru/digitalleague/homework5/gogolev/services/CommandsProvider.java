package ru.digitalleague.homework5.gogolev.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.factories.CommandsAbstractFactory;
import ru.digitalleague.homework5.gogolev.commands.CommandsDescription;
import ru.digitalleague.homework5.gogolev.exceptions.NoSuchCommandException;


@Slf4j
@Component
@RequiredArgsConstructor
public class CommandsProvider {
    private final ApplicationContextProvider contextProvider;

    public Command<?> provideCommand(String input) {
        String commandName = input.split("_")[0];
        return provideFactory(commandName).create(input);
    }
    private CommandsAbstractFactory provideFactory(String commandName) {
        CommandsAbstractFactory factory;
        try {
            CommandsDescription description = CommandsDescription.valueOf(commandName.toUpperCase());
            factory = contextProvider.getContext().getBean(description.getFactoryName(), CommandsAbstractFactory.class);
        } catch (IllegalArgumentException | NullPointerException e){
            String message = "No such command "+ commandName;
            log.error(message);
            throw new NoSuchCommandException(message);
        }
        return factory;
    }



}

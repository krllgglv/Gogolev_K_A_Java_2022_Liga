package ru.digitalleague.gogolev.commands.factories;

import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.command.Command;
import ru.digitalleague.gogolev.commands.command.ExitCommand;

@Component
public class ExitCommandFactory implements CommandsAbstractFactory {

    @Override
    public Command<?> create(String... parameters) {
        return new ExitCommand();
    }
}

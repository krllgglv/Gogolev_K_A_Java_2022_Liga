package ru.digitalleague.homework5.gogolev.commands.factories;

import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.system_commands.ExitCommand;

@Component
public class ExitCommandFactory implements CommandsAbstractFactory {

    @Override
    public Command<?> create(String... parameters) {
        return new ExitCommand();
    }
}

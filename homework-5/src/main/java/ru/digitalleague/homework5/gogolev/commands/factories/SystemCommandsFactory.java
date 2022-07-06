package ru.digitalleague.homework5.gogolev.commands.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.system_commands.ExitCommand;
import ru.digitalleague.homework5.gogolev.commands.system_commands.ShowHelpCommand;
import ru.digitalleague.homework5.gogolev.exceptions.NoSuchCommandException;

@Slf4j
@Component
public class SystemCommandsFactory implements CommandsAbstractFactory {
    @Override
    public Command<?> create(String input) {
        String commandName = input.split("_")[0];
        return switch (commandName.toUpperCase()) {
            case "HELP" -> new ShowHelpCommand();
            case "EXIT" -> new ExitCommand();
            default -> {
                String message = "No such command" + commandName;
                log.error(message);
                throw new NoSuchCommandException(message);
            }
        };

    }
}

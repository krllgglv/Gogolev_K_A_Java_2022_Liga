package ru.digitalleague.gogolev.commands.factories;

import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.Command;
import ru.digitalleague.gogolev.commands.system_commands.ShowHelpCommand;

@Component
public class HelpCommandFactory implements CommandsAbstractFactory{
    @Override
    public Command<?> create(String ... parameters) {
        return new ShowHelpCommand();
    }
}

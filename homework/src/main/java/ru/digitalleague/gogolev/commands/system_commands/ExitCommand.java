package ru.digitalleague.gogolev.commands.system_commands;

import ru.digitalleague.gogolev.commands.AbstractCommand;

public class ExitCommand extends AbstractCommand<Boolean> {
    @Override
    public Boolean execute() {
        System.exit(0);
        return true;
    }
}

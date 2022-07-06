package ru.digitalleague.homework5.gogolev.commands.system_commands;

import ru.digitalleague.homework5.gogolev.commands.AbstractCommand;

public class ExitCommand extends AbstractCommand<Boolean> {
    @Override
    public Boolean execute() {
        System.exit(0);
        return true;
    }
}

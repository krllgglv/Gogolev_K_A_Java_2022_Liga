package ru.digitalleague.gogolev.commands.command;

public class ExitCommand implements Command<Boolean> {
    @Override
    public Boolean execute() {
        System.exit(0);
        return true;
    }
}

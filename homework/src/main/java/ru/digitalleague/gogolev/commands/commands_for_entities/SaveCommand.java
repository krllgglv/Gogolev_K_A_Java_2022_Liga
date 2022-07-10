package ru.digitalleague.gogolev.commands.commands_for_entities;

import ru.digitalleague.gogolev.commands.AbstractEntityCommand;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

public class SaveCommand extends AbstractEntityCommand<Void> {
    public SaveCommand(UsersService usersService, TasksService tasksService) {
        super(usersService, tasksService);
    }

    @Override
    public Void execute() {
        tasksService.saveStateToFile();
        return null;
    }
}

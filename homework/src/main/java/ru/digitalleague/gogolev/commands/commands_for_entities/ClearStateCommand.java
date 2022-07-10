package ru.digitalleague.gogolev.commands.commands_for_entities;

import ru.digitalleague.gogolev.commands.AbstractEntityCommand;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

public class ClearStateCommand extends AbstractEntityCommand<Boolean> {

    public ClearStateCommand(UsersService usersService, TasksService tasksService) {
        super(usersService, tasksService);
    }

    @Override
    public Boolean execute() {
        tasksService.clearState();
        usersService.clearState();
        return true;
    }
}

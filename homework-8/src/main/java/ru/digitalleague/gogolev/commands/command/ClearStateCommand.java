package ru.digitalleague.gogolev.commands.command;

import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

public class ClearStateCommand implements Command<Boolean> {
    private final UsersService usersService;
    private final TasksService tasksService;

    public ClearStateCommand(UsersService usersService, TasksService tasksService) {
        super();
        this.usersService = usersService;
        this.tasksService = tasksService;

    }

    @Override
    public Boolean execute() {
        tasksService.clearState();
        usersService.clearState();
        return true;
    }
}

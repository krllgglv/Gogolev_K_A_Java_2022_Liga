package ru.digitalleague.homework5.gogolev.commands;

import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;

public abstract class AbstractEntityCommand<R> extends AbstractCommand<R>{
    protected UsersService usersService;
    protected TasksService tasksService;

    protected AbstractEntityCommand(UsersService usersService, TasksService tasksService) {
        this.usersService = usersService;
        this.tasksService = tasksService;
    }
}

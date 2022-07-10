package ru.digitalleague.gogolev.commands;

import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

import java.util.Map;

public abstract class AbstractParametrizedEntityCommand<R> extends AbstractEntityCommand<R> {

    protected Map<String, String> parameters;

    protected AbstractParametrizedEntityCommand( UsersService usersService, TasksService tasksService, Map<String, String> params) {
        super(usersService, tasksService);
        this.parameters = params;
    }

}

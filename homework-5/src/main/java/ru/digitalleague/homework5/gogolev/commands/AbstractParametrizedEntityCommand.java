package ru.digitalleague.homework5.gogolev.commands;

import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;

import java.util.Map;

public abstract class AbstractParametrizedEntityCommand<R> extends AbstractEntityCommand<R> {

    protected Map<String, String> parameters;

    protected AbstractParametrizedEntityCommand( UsersService usersService, TasksService tasksService, String input) {
        super(usersService, tasksService);
        this.parameters = validateAndGetParameters(input);
    }

     protected abstract Map<String, String> validateAndGetParameters(String input);
}

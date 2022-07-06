package ru.digitalleague.homework5.gogolev.commands.commands_for_entities;

import ru.digitalleague.homework5.gogolev.commands.AbstractEntityCommand;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;

public class SaveStateCommand extends AbstractEntityCommand<Void> {
    public SaveStateCommand(UsersService usersService, TasksService tasksService) {
        super(usersService, tasksService);
    }

    @Override
    public Void execute() {
        tasksService.saveStateToFile();
        return null;
    }
}

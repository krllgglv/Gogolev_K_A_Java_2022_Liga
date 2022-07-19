package ru.digitalleague.gogolev.commands.command;

import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

public class SaveCommand implements Command<Void> {
    private final TasksService tasksService;
    private final UsersService usersService;
    public SaveCommand(TasksService tasksService, UsersService usersService) {
        super();
        this.tasksService = tasksService;
        this.usersService = usersService;
    }

    @Override
    public Void execute() {
        tasksService.saveStateToFile();
        usersService.saveStateToFile();
        return null;
    }
}

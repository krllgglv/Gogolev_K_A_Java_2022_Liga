package ru.digitalleague.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.command.Command;
import ru.digitalleague.gogolev.commands.command.SaveCommand;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

@Component
@RequiredArgsConstructor
public class SaveCommandFactory implements CommandsAbstractFactory {
    private final TasksService tasksService;
    private final UsersService usersService;

    @Override
    public Command<?> create(String... parameters) {
        return new SaveCommand(tasksService, usersService);
    }
}

package ru.digitalleague.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.Command;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;
import ru.digitalleague.gogolev.commands.commands_for_entities.SaveCommand;

@Component
@RequiredArgsConstructor
public class SaveCommandFactory implements CommandsAbstractFactory{
    private final UsersService usersService;
    private final TasksService tasksService;
    @Override
    public Command<?> create(String ... parameters) {
        return new SaveCommand(usersService, tasksService);
    }
}

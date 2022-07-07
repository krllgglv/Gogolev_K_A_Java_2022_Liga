package ru.digitalleague.homework5.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.homework5.gogolev.commands.Command;
import ru.digitalleague.homework5.gogolev.commands.commands_for_entities.SaveCommand;
import ru.digitalleague.homework5.gogolev.services.TasksService;
import ru.digitalleague.homework5.gogolev.services.UsersService;

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

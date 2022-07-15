package ru.digitalleague.gogolev.commands.factories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.digitalleague.gogolev.commands.command.Command;
import ru.digitalleague.gogolev.commands.command.ClearStateCommand;
import ru.digitalleague.gogolev.services.TasksService;
import ru.digitalleague.gogolev.services.UsersService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClearStateCommandFactory implements CommandsAbstractFactory {
    private final UsersService usersService;
    private final TasksService tasksService;

    @Override
    public Command<?> create(String... parameters) {
        return new ClearStateCommand(usersService, tasksService);
    }


}
